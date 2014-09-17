
#include <iostream>
#include <fstream>
#include <sstream>
#include <math.h>
#include <vector>
#include <string>

#include "Angel-yjc.h"

using namespace std;

/*------------- Variable declarations ----------------------*/

typedef Angel::vec3 color3;
typedef Angel::vec3 point3;
typedef Angel::vec4 color4;
typedef Angel::vec4 point4;


GLuint Angel::InitShader(const char* vShaderFile, const char* fShaderFile);

/* Tessalation units */
struct TessUnit {
    int noOfTessUnitVertices; /* number of vertices per tesselation unit */
    vector<point3> tessVertices;
    vector<color3> tessVertexColors;
    vector<point3> tessVertexNormals;
    vec4 planeNormal;
    color4 ambientColor;
    color4 diffuseColor;
    color4 specularColor;
    float shininessCoeff;
};

GLuint program; /* shader program object id */
GLuint simpleProgram;
GLuint lightingProgram;
GLuint shadowProgram;
GLuint sphere_buffer; /* vertex buffer object id for cube */
GLuint floor_buffer; /* vertex buffer object id for floor */
GLuint xaxis_buffer;
GLuint yaxis_buffer;
GLuint zaxis_buffer;
GLuint shadow_buffer;

// Projection transformation parameters

GLfloat fovy = 45.0; // Field-of-view in Y direction angle (in degrees)
GLfloat aspect; // Viewport aspect ratio
GLfloat zNear = 0.5, zFar = 20.0; // near and far field of view

GLfloat angle = 0.0; // rotation angle in degrees
GLfloat angleIncreament = 0.5; // angle by which rotation increases
vec4 rollingVtx1(-4.0, 1.0, 4.0, 1.0);
vec4 rollingVtx2(3.0, 1.0, -4.0, 1.0);
vec4 rollingVtx3(-3.0, 1.0, -3.0, 1.0);
int rollingLeg = 0;

vec4 rollingAxis = rollingVtx1;
vec4 rollingStart = rollingVtx1;
vec4 rolledPosition(0.0, 0.0, 0.0, 0.0);
mat4 init_rotation(1.0);
mat4 accumulated_rotation = init_rotation;

vec4 init_eye(7.0, 3.0, -10.0, 1.0); // initial viewer position
vec4 eye = init_eye; // current viewer position

int rollingBeginFlag = 0;
int animationFlag = 0; // 1: animation; 0: non-animation. Toggled by key 'a' or 'A'
int smoothRoll = 1; // whether to include accumulation rotation
int enableShadow = 0; // 0 = no shadow, 1 = shadow
int enableCorrectShadow = 0; // with decal
int enableLighting = 0; // enables light

int smoothShading = 0;
int prvLightingMode = -1;
int enableWireframe = 0;
int lightType = 0; // 0 = none, 1 = directional, 2 = spot light, 3 = point source 

int noOfTessalationUnits = 0; /* number of tesselation units */

vec4 shadow_light(-14.0, 12.0, -3.0, 1.0);

TessUnit *sphereUnit;
TessUnit *floorUnit;
TessUnit *xAxisUnit;
TessUnit *yAxisUnit;
TessUnit *zAxisUnit;

int glWindowHeight = 520; /* window height */
int glWindowWidth = 520; /* window width */
int glWindowCenterX = (glWindowWidth / 2), glWindowCenterY = (glWindowHeight / 2); /* window centers */
color3 glWindowColor(0.529, 0.807, 0.92); //0.0

/*------ Lighting and shading ---------------*/

color4 globalAmbientLight(1.0, 1.0, 1.0, 1.0);
color4 ambientLight(0.0, 0.0, 0.0, 1.0);
color4 diffuseLight(1.0, 1.0, 1.0, 1.0);
color4 specularLight(1.0, 1.0, 1.0, 1.0);
point4 directionalLightPosition(0.0, 0.0, 1.0, 0.0);
point4 positionalLightPosition(-6.0, 0.0, -4.5, 1.0);

/*------------- utility methods -------------*/

/* reads the input file and populates the list with given data */
void prepareInput();

/* trims a string */
string trim(string strToTrim);

/* prints the sphere units */
void printTessUnits(TessUnit *tessUnit);

/*------------------ object setup functions -------------------------*/

void floor();
void axis();
void shadow(mat4 mv, mat4 lookAt, mat4 p);
void prepareShaderForDistantSource(TessUnit *tessUnit, mat4 mv);
void prepareShaderForNearSource(TessUnit *tessUnit, mat4 mv);

void init();
void drawObject(GLuint buffer, int num_vertices);
int bufferDataSize(TessUnit *tessUnit);
int bufferColorSize(TessUnit *tessUnit);
int bufferNormalSize(TessUnit *tessUnit);
void prepareSphereNormal();
void loadShader();
void prepareBufferForDisplay();


/* ----------------- gl callback methods -----------------------------*/
void display();
void idle();
void reshape(int width, int height);
void keyboard(unsigned char key, int x, int y);
void mouse(int button, int state, int x, int y);
void contextMenu(int id);

/*------------- Setting up sphere, and the scene methods -------------*/

int main(int argc, char **argv) {

    int status;

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH);
    glutInitWindowSize(glWindowWidth, glWindowHeight);

    glutCreateWindow("Rolling Sphere");

    /* Call glewInit() and error checking */
    status = glewInit();
    if (GLEW_OK != status) {
        printf("Error: glewInit failed: %s\n", (char*) glewGetErrorString(status));
        exit(1);
    }
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutMouseFunc(mouse);

    int shadowMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("Yes", 3);
    glutAddMenuEntry("No", 4);

    int lightingMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("Yes", 5);
    glutAddMenuEntry("No", 6);

    int shadingMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("Flat", 7);
    glutAddMenuEntry("Smooth", 8);

    int lightMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("Spot", 9);
    glutAddMenuEntry("Point", 10);

    int viewMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("Yes", 11);
    glutAddMenuEntry("No", 12);

    glutCreateMenu(contextMenu);
    glutAddMenuEntry("Default View Point", 1);
    glutAddMenuEntry("Quit", 2);

    glutAddSubMenu("Shadow", shadowMenu);
    glutAddSubMenu("Enable Light", lightingMenu);
    glutAddSubMenu("Enable Shading", shadingMenu);
    glutAddSubMenu("Lighting", lightMenu);
    glutAddSubMenu("Wireframe", viewMenu);

    glutAttachMenu(GLUT_LEFT_BUTTON);

    init();
    glutMainLoop();
    return 0;
}

/*------------- gl callback functions ---------------*/

void display() {

    loadShader();
    prepareBufferForDisplay();
    prepareSphereNormal();

    GLuint model_view; // model-view matrix uniform shader variable location
    GLuint projection; // projection matrix uniform shader variable location

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glUseProgram(program); // Use the shader program

    model_view = glGetUniformLocation(program, "model_view");
    projection = glGetUniformLocation(program, "projection");

    /*---  Set up and pass on Projection matrix to the shader ---*/
    mat4 p = Perspective(fovy, aspect, zNear, zFar);
    glUniformMatrix4fv(projection, 1, GL_TRUE, p); // GL_TRUE: matrix is row-major

    /*---  Set up and pass on Model-View matrix to the shader ---*/
    // eye is a global variable of vec4 set to init_eye and updated by keyboard()
    vec4 at(0.0, 0.0, 0.0, 1.0);
    vec4 up(0.0, 1.0, 0.0, 0.0);

    // drawing axis
    mat4 mv = Translate(0, 0.1, 0) * LookAt(eye, at, up);

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    drawObject(xaxis_buffer, xAxisUnit->noOfTessUnitVertices); // draw the axis
    drawObject(yaxis_buffer, yAxisUnit->noOfTessUnitVertices); // draw the axis
    drawObject(zaxis_buffer, zAxisUnit->noOfTessUnitVertices); // draw the axis

    mv = LookAt(eye, at, up) * Scale(1.1, 1.1, 1.1) * Rotate(5.0, 0.0, 2.0, 0.0);

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    
    if(enableLighting == 1) {
        if(lightType == 1) {
            // distant light source
            prepareShaderForDistantSource(floorUnit, mv);
        } else {
            // point or spot light source
            prepareShaderForNearSource(floorUnit, mv);
        }
    }

    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    if (enableCorrectShadow == 1) {
        glDepthMask(GL_FALSE);
        glColorMask(GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE);
    }

    drawObject(floor_buffer, floorUnit->noOfTessUnitVertices); // draw the floor

    if (enableCorrectShadow == 1) {
        glDepthMask(GL_TRUE);
        glColorMask(GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE);
    }


    mat4 translation = Translate(rolledPosition.x, rolledPosition.y, rolledPosition.z)
            * Translate(rollingStart.x, rollingStart.y, rollingStart.z);
    mat4 rotation = Rotate(angle, rollingAxis.x, rollingAxis.y, rollingAxis.z);

    mv = LookAt(eye, at, up)
            * translation
            * rotation
            * accumulated_rotation
            ;

    if (smoothRoll == 1) {
        accumulated_rotation = rotation;
    } else {
        accumulated_rotation = init_rotation;
    }

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    
    if(enableLighting == 1) {
        if(lightType == 1) {
            // distant light source
            prepareShaderForDistantSource(sphereUnit, mv);
        } else {
            // point or spot light source
            prepareShaderForNearSource(sphereUnit, mv);
        }
    }
    if (enableWireframe == 1) {
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    } else {
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }

    drawObject(sphere_buffer, sphereUnit->noOfTessUnitVertices); // draw the axis

    if (enableShadow == 1) {
        mat4 nmv = translation
                * rotation
                * accumulated_rotation
                ;
        // create the shadow buffer ---------------------------
        shadow(nmv, LookAt(eye, at, up), p);
    }

    glutSwapBuffers();
}

void reshape(int width, int height) {
    glViewport(0, 0, width, height);
    aspect = (GLfloat) width / (GLfloat) height;
    glutPostRedisplay();
}

void idle() {

    angle += angleIncreament;

    if (rollingLeg == 0) {
        // along A to B
        rollingAxis = rollingVtx2 - rollingVtx1;
    } else if (rollingLeg == 1) {
        // along A to B
        rollingAxis = rollingVtx3 - rollingVtx2;
    } else {
        // along C to A
        rollingAxis = rollingVtx1 - rollingVtx3;
    }
    // normal vector at rolling position with the plane
    vec4 up(0.0, -1.0, 0.0, 0.0);

    // calculating the distance traversed
    float linearTranslation = angle * (((float) (2 * M_PI * 1)) / (float) 360);
    rolledPosition = linearTranslation * normalize(rollingAxis);
    float totalDistance = sqrt(rollingAxis.x * rollingAxis.x +
            rollingAxis.y * rollingAxis.y + rollingAxis.z * rollingAxis.z);

    rollingAxis = normalize(cross(rollingAxis, up));

    //cout << "total : " << totalDistance << " travelled : " << (totalDistance - linearTranslation) << endl;

    if (rollingLeg == 0 && (totalDistance - linearTranslation) < 0.0) {
        rollingLeg = 1;
        rollingStart = rollingVtx2;
        angle = 0.0;

    } else if (rollingLeg == 1 && (totalDistance - linearTranslation) <= 0.0) {
        rollingLeg = 2;
        rollingStart = rollingVtx3;
        angle = 0.0;
    } else if (rollingLeg == 2 && (totalDistance - linearTranslation) <= 0.0) {
        rollingLeg = 0;
        rollingStart = rollingVtx1;
        angle = 0.0;
    }

    glutPostRedisplay();

}

void keyboard(unsigned char key, int x, int y) {
    switch (key) {
        case 'q': case 'Q':
            exit(EXIT_SUCCESS);
            break;

        case 'X': eye[0] += 1.0;
            break;
        case 'x': eye[0] -= 1.0;
            break;
        case 'Y': eye[1] += 1.0;
            break;
        case 'y': eye[1] -= 1.0;
            break;
        case 'Z': eye[2] += 1.0;
            break;
        case 'z': eye[2] -= 1.0;
            break;
        case 'b': case 'B': // Toggle between animation and non-animation
            if (rollingBeginFlag == 0) {
                // rolling has not started, so start it
                rollingBeginFlag = 1;
                animationFlag = 1;
                glutIdleFunc(idle);
            }

            break;
    }
    glutPostRedisplay();
}

void mouse(int button, int state, int x, int y) {
    if (state == GLUT_DOWN) {
        cout << "x = " << x << " y = " << y << endl;
        if (button == GLUT_RIGHT_BUTTON && rollingBeginFlag == 1) {
            if (animationFlag == 1) {
                // its rolling, so stop it
                glutIdleFunc(NULL);
                animationFlag = 0;
            } else {
                // its not rolling, so stop it
                animationFlag = 1;
                glutIdleFunc(idle);
            }

        }
    }

}

void contextMenu(int id) {
    switch (id) {
        case 1: eye = init_eye;
            break;
        case 2: exit(EXIT_SUCCESS);
            break;
        case 3: enableShadow = 1;
            enableCorrectShadow = 1;
            break;
        case 4: enableShadow = 0;
            enableCorrectShadow = 0;
            break;
        case 5: enableLighting = 1; lightType = 1;
            break;
        case 6: enableLighting = 0; lightType = 0;
            break;
        case 7: smoothShading = 0;
            break;
        case 8: smoothShading = 1;
            break;
        case 9: lightType = 2;
            break;
        case 10: lightType = 3;
            break;
        case 11: enableWireframe = 1;
            break;
        case 12: enableWireframe = 0;
            break;

    }
}

/*------------------ object setup functions -------------------------*/

void floor() {
    floorUnit = new TessUnit;
    floorUnit->noOfTessUnitVertices = 6;

    // light and shade
    floorUnit->ambientColor = color4(0.2, 0.2, 0.2, 1.0);
    floorUnit->diffuseColor = color4(0.0, 1.0, 0.0, 1.0);
    floorUnit->specularColor = color4(1.0, 0.0, 0.0, 1.0);
    floorUnit->shininessCoeff = 100.0;

    // triangle 1
    floorUnit->tessVertices.push_back(point3(-5, 0, -4));
    floorUnit->tessVertices.push_back(point3(5, 0, -4));
    floorUnit->tessVertices.push_back(point3(5, 0, 8));

    floorUnit->tessVertexColors.push_back(color3(0, 1, 0));
    floorUnit->tessVertexColors.push_back(color3(0, 1, 0));
    floorUnit->tessVertexColors.push_back(color3(0, 1, 0));

    // triangle 2
    floorUnit->tessVertices.push_back(point3(-5, 0, -4));
    floorUnit->tessVertices.push_back(point3(-5, 0, 8));
    floorUnit->tessVertices.push_back(point3(5, 0, 8));

    floorUnit->tessVertexColors.push_back(color3(0, 1, 0));
    floorUnit->tessVertexColors.push_back(color3(0, 1, 0));
    floorUnit->tessVertexColors.push_back(color3(0, 1, 0));

    // normals
    floorUnit->planeNormal = vec4(0.0, 1.0, 0.0, 0.0); // the up vector

    floorUnit->tessVertexNormals.push_back(point3(0.0, 1.0, 0.0));
    floorUnit->tessVertexNormals.push_back(point3(0.0, 1.0, 0.0));
    floorUnit->tessVertexNormals.push_back(point3(0.0, 1.0, 0.0));

    floorUnit->tessVertexNormals.push_back(point3(0.0, 1.0, 0.0));
    floorUnit->tessVertexNormals.push_back(point3(0.0, 1.0, 0.0));
    floorUnit->tessVertexNormals.push_back(point3(0.0, 1.0, 0.0));

}

void axis() {
    xAxisUnit = new TessUnit;
    xAxisUnit->noOfTessUnitVertices = 2;

    // x-axis
    xAxisUnit->tessVertices.push_back(point3(0, 0, 0));
    xAxisUnit->tessVertices.push_back(point3(200, 0, 0));

    xAxisUnit->tessVertexColors.push_back(color3(1, 0, 0));
    xAxisUnit->tessVertexColors.push_back(color3(1, 0, 0));

    // y-axis
    yAxisUnit = new TessUnit;
    yAxisUnit->noOfTessUnitVertices = 2;

    yAxisUnit->tessVertices.push_back(point3(0, 0, 0));
    yAxisUnit->tessVertices.push_back(point3(0, 300, 0));

    yAxisUnit->tessVertexColors.push_back(color3(1, 0, 1));
    yAxisUnit->tessVertexColors.push_back(color3(1, 0, 1));

    // z-axis
    zAxisUnit = new TessUnit;
    zAxisUnit->noOfTessUnitVertices = 2;

    zAxisUnit->tessVertices.push_back(point3(0, 0, 0));
    zAxisUnit->tessVertices.push_back(point3(0, 0, 300));

    zAxisUnit->tessVertexColors.push_back(color3(0, 0, 1));
    zAxisUnit->tessVertexColors.push_back(color3(0, 0, 1));
}

void shadow(mat4 mv, mat4 lookAt, mat4 p) {

    GLuint model_view, sLight, at, projection;
    glUseProgram(shadowProgram); // Use the shader program

    int num_vertices = sphereUnit->noOfTessUnitVertices;

    model_view = glGetUniformLocation(shadowProgram, "model_view");
    sLight = glGetUniformLocation(shadowProgram, "sLight");
    at = glGetUniformLocation(shadowProgram, "at");
    projection = glGetUniformLocation(shadowProgram, "projection");

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv);
    glUniformMatrix4fv(at, 1, GL_TRUE, lookAt);
    glUniformMatrix4fv(projection, 1, GL_TRUE, p);
    glUniform4fv(sLight, 1, shadow_light);

    glBindBuffer(GL_ARRAY_BUFFER, shadow_buffer);

    /*----- Set up vertex attribute arrays for each vertex attribute -----*/
    GLuint vPosition = glGetAttribLocation(shadowProgram, "vPosition");
    glEnableVertexAttribArray(vPosition);
    glVertexAttribPointer(vPosition, 3, GL_FLOAT, GL_FALSE, 0,
            BUFFER_OFFSET(0));

    glDrawArrays(GL_TRIANGLES, 0, num_vertices);

    /*--- Disable each vertex attribute array being enabled ---*/
    glDisableVertexAttribArray(vPosition);

}

void prepareShaderForDistantSource(TessUnit *tessUnit, mat4 mv) {
    
    /*if(enableLighting != 1 || lightType != 1) {
        // not distance source
        return;
    }*/
    mat3 normal_matrix = NormalMatrix(mv, 1);

    //cout << "\n---------------------------------\n normal_matrix : " << normal_matrix << endl;

    glUniformMatrix3fv(glGetUniformLocation(program, "Normal_Matrix"),
            1, GL_TRUE, normal_matrix);
    // attenuation = 1

    color4 ambient_product = ambientLight * tessUnit->ambientColor;
    color4 diffuse_product = diffuseLight * tessUnit->diffuseColor;
    color4 specular_product = specularLight * tessUnit->specularColor;

    color4 global_ambient_product = globalAmbientLight * tessUnit->ambientColor;

    float constAtt = 1.0;
    float linearAtt = 0.0;
    float quadAtt = 0.0;

    glUniform4fv(glGetUniformLocation(program, "AmbientProduct"),
            1, ambient_product);
    glUniform4fv(glGetUniformLocation(program, "DiffuseProduct"),
            1, diffuse_product);
    glUniform4fv(glGetUniformLocation(program, "SpecularProduct"),
            1, specular_product);
    glUniform4fv(glGetUniformLocation(program, "GlobalAmbientProduct"),
            1, global_ambient_product);


    vec4 light_position_eyeFrame = directionalLightPosition;
    glUniform4fv(glGetUniformLocation(program, "LightPosition"),
            1, light_position_eyeFrame);

    glUniform1f(glGetUniformLocation(program, "ConstAtt"),
            constAtt);
    glUniform1f(glGetUniformLocation(program, "LinearAtt"),
            linearAtt);
    glUniform1f(glGetUniformLocation(program, "QuadAtt"),
            quadAtt);

    glUniform1f(glGetUniformLocation(program, "Shininess"),
            tessUnit->shininessCoeff);

    glUniform1i(glGetUniformLocation(program, "DistanceLight"),
            1);

    // spot and point source - just putting default values

    glUniform1f(glGetUniformLocation(program, "SpotlightCutOffAngle"),
            180.0);
    
    glUniform1f(glGetUniformLocation(program, "SpotlightExp"),
            1.0);
    
    glUniform4fv(glGetUniformLocation(program, "SpotlightPosition"),
            1, positionalLightPosition);

}

void prepareShaderForNearSource(TessUnit *tessUnit, mat4 mv) {

    mat3 normal_matrix = NormalMatrix(mv, 1);

    glUniformMatrix3fv(glGetUniformLocation(program, "Normal_Matrix"),
            1, GL_TRUE, normal_matrix);
    // attenuation = 1

    color4 ambient_product = ambientLight * tessUnit->ambientColor;
    color4 diffuse_product = diffuseLight * tessUnit->diffuseColor;
    color4 specular_product = specularLight * tessUnit->specularColor;

    color4 global_ambient_product = globalAmbientLight * tessUnit->ambientColor;

    glUniform4fv(glGetUniformLocation(program, "AmbientProduct"),
            1, ambient_product);
    glUniform4fv(glGetUniformLocation(program, "DiffuseProduct"),
            1, diffuse_product);
    glUniform4fv(glGetUniformLocation(program, "SpecularProduct"),
            1, specular_product);
    glUniform4fv(glGetUniformLocation(program, "GlobalAmbientProduct"),
            1, global_ambient_product);


    float constAtt = 2.0;
    float linearAtt = 0.01;
    float quadAtt = 0.001;
    
    float spotExp = 5.0;
    float spotCutOff = 20.0;
    
    vec4 light_position_eyeFrame = mv * shadow_light;
    glUniform4fv(glGetUniformLocation(program, "LightPosition"),
            1, light_position_eyeFrame);

    glUniform1f(glGetUniformLocation(program, "ConstAtt"),
            constAtt);
    glUniform1f(glGetUniformLocation(program, "LinearAtt"),
            linearAtt);
    glUniform1f(glGetUniformLocation(program, "QuadAtt"),
            quadAtt);

    glUniform1f(glGetUniformLocation(program, "Shininess"),
            tessUnit->shininessCoeff);

    // for spot and distant lights-----------------------------
    glUniform1i(glGetUniformLocation(program, "DistanceLight"),
            0);

    if(lightType == 2) {
        glUniform1f(glGetUniformLocation(program, "SpotlightCutOffAngle"),
            spotCutOff);
    } else {
        glUniform1f(glGetUniformLocation(program, "SpotlightCutOffAngle"),
            180.0);
    }

    glUniform1f(glGetUniformLocation(program, "SpotlightExp"),
            spotExp);
    
    glUniform4fv(glGetUniformLocation(program, "SpotlightPosition"),
            1, positionalLightPosition);


}

void prepareSphereNormal() {
    sphereUnit->tessVertexNormals.clear();
    if (smoothShading == 1) {
        // calculate each point normal
        int counter = 0;

        while (counter < sphereUnit->noOfTessUnitVertices) {
            sphereUnit->tessVertexNormals.push_back(sphereUnit->tessVertices[counter]);
            counter++;
        }
    } else {
        // calculate the plane normal
        int counter = 0;

        while (counter < sphereUnit->noOfTessUnitVertices) {
            point3 p0 = sphereUnit->tessVertices[counter];
            point3 p1 = sphereUnit->tessVertices[counter + 1];
            point3 p2 = sphereUnit->tessVertices[counter + 2];

            vec3 n = normalize(cross((p1 - p0), (p2 - p1)));

            sphereUnit->tessVertexNormals.push_back(n);
            sphereUnit->tessVertexNormals.push_back(n);
            sphereUnit->tessVertexNormals.push_back(n);

            counter = counter + 3;
        }
    }
}

void prepareBufferForDisplay() {
    int dataSize = 0, colorOrNormalSize = 0;

    // drawing floor ---------------------------------
    dataSize = bufferDataSize(floorUnit);
    if (enableLighting == 1) {
        colorOrNormalSize = bufferNormalSize(floorUnit);
    } else {
        colorOrNormalSize = bufferColorSize(floorUnit);
    }
    
    glGenBuffers(1, &floor_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, floor_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorOrNormalSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(floorUnit->tessVertices[0]));
    if (enableLighting == 1) {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(floorUnit->tessVertexNormals[0]));
    } else {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(floorUnit->tessVertexColors[0]));
    }

    // drawing sphere ---------------------------------
    dataSize = bufferDataSize(sphereUnit);
    if (enableLighting == 1) {
        colorOrNormalSize = bufferNormalSize(sphereUnit);
    } else {
        colorOrNormalSize = bufferColorSize(sphereUnit);
    }

    glGenBuffers(1, &sphere_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, sphere_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorOrNormalSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(sphereUnit->tessVertices[0]));
    if (enableLighting == 1) {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(sphereUnit->tessVertexNormals[0]));
    } else {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(sphereUnit->tessVertexColors[0]));
    }

    // shadow buffer -----------------
    glGenBuffers(1, &shadow_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, shadow_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize, NULL, GL_STATIC_DRAW);
    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(sphereUnit->tessVertices[0]));
}

int bufferDataSize(TessUnit *tessUnit) {
    return ((tessUnit->tessVertices).size() * sizeof (point3));
}

int bufferColorSize(TessUnit *tessUnit) {
    return ((tessUnit->tessVertexColors).size() * sizeof (color3));
}

int bufferNormalSize(TessUnit *tessUnit) {
    return ((tessUnit->tessVertexNormals).size() * sizeof (point3));
}

void init() {

    // sphere data
    prepareInput();

    // floor data
    floor();

    // axis data
    axis();

    int dataSize = 0, colorSize = 0;

    // drawing axis - x ----------------------------
    dataSize = bufferDataSize(xAxisUnit);
    colorSize = bufferColorSize(xAxisUnit);

    glGenBuffers(1, &xaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, xaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(xAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(xAxisUnit->tessVertexColors[0]));

    // drawing axis - y ----------------------------
    dataSize = bufferDataSize(yAxisUnit);
    colorSize = bufferColorSize(yAxisUnit);

    glGenBuffers(1, &yaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, yaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(yAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(yAxisUnit->tessVertexColors[0]));

    // drawing axis - z ----------------------------
    dataSize = bufferDataSize(zAxisUnit);
    colorSize = bufferColorSize(zAxisUnit);

    glGenBuffers(1, &zaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, zaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(zAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(zAxisUnit->tessVertexColors[0]));

    simpleProgram = InitShader("vshader42.glsl", "fshader42.glsl");
    lightingProgram = InitShader("lshader42.glsl", "fshader42.glsl");
    shadowProgram = InitShader("sshader42.glsl", "fshader42.glsl");

    glEnable(GL_DEPTH_TEST);
    glClearColor(0.529, 0.807, 0.92, 0.0);
    glLineWidth(1.0);
}

void drawObject(GLuint buffer, int num_vertices) {
    //--- Activate the vertex buffer object to be drawn ---//
    glBindBuffer(GL_ARRAY_BUFFER, buffer);

    /*----- Set up vertex attribute arrays for each vertex attribute -----*/
    GLuint vPosition = glGetAttribLocation(program, "vPosition");
    glEnableVertexAttribArray(vPosition);
    glVertexAttribPointer(vPosition, 3, GL_FLOAT, GL_FALSE, 0,
            BUFFER_OFFSET(0));

    GLuint vColor, vNormal;


    if (enableLighting == 1) {
        vNormal = glGetAttribLocation(program, "vNormal");
        glEnableVertexAttribArray(vNormal);
        glVertexAttribPointer(vNormal, 3, GL_FLOAT, GL_FALSE, 0,
                BUFFER_OFFSET(sizeof (point3) * num_vertices));
    } else {
        // the offset is the (total) size of the previous vertex attribute array(s)
        vColor = glGetAttribLocation(program, "vColor");
        glEnableVertexAttribArray(vColor);
        glVertexAttribPointer(vColor, 3, GL_FLOAT, GL_FALSE, 0,
                BUFFER_OFFSET(sizeof (point3) * num_vertices));
    }

    /* Draw a sequence of geometric objs (triangles) from the vertex buffer
       (using the attributes specified in each enabled vertex attribute array) */
    if (num_vertices < 3) {
        glDrawArrays(GL_LINES, 0, num_vertices);
    } else {
        glDrawArrays(GL_TRIANGLES, 0, num_vertices);
    }

    /*--- Disable each vertex attribute array being enabled ---*/
    glDisableVertexAttribArray(vPosition);
    glDisableVertexAttribArray(vColor);
}

void loadShader() {
    // Load shaders and create a shader program (to be used in display())
    if (prvLightingMode == enableLighting) {
        // shading mode has not changed since last frame refres, so
        // don't do anything
        return;
    }

    if (enableLighting == 1) {
        program = lightingProgram;
    } else {
        program = simpleProgram;
    }

    prvLightingMode = enableLighting;

}

/*------------- Utility functions -------------------*/
string trim(string strToTrim) {
    string trimmedString(strToTrim);

    size_t nonWhiteCharPos = trimmedString.find_last_not_of(' ');

    if (nonWhiteCharPos != string::npos) {
        // Right trim
        trimmedString.erase(nonWhiteCharPos + 1);
        nonWhiteCharPos = trimmedString.find_first_not_of(' ');

        // Left trim
        if (nonWhiteCharPos != string::npos) {
            trimmedString.erase(0, nonWhiteCharPos);
        }
    } else {
        // A string with just whitespace
        trimmedString.erase(trimmedString.begin(), trimmedString.end());
    }

    return trimmedString;
}

void printTessUnits(TessUnit *tessUnit) {
    int counter = 0;
    cout << "tess unit number : " << tessUnit->noOfTessUnitVertices << endl;
    while (counter < tessUnit->noOfTessUnitVertices) {
        point3 point = tessUnit->tessVertices[counter];
        //cout <<  counter + 1 << endl;

        cout << "(" << point.x << ", "
                << point.y << ", "
                << point.z << ")";
        cout << " normal ";
        point = tessUnit->tessVertexNormals[counter];
        cout << "(" << point.x << ", "
                << point.y << ", "
                << point.z << ")" << endl;

        counter++;
    }
}

void prepareInput() {
    string filePath;
    cout << "Please enter a file path" << endl;
    getline(cin, filePath);

    //debug
    //filePath = "/home/papa/studyspace/CS6533/HW/HW3/sphere.1024";

    std::ifstream fileStream(filePath.c_str());

    string nextLine;
    getline(fileStream, nextLine);
    stringstream myStream(nextLine);

    myStream >> noOfTessalationUnits;

    cout << "noOfTessalationUnits :: " << noOfTessalationUnits << endl;

    if (noOfTessalationUnits == 0) {
        cout << "Nothing to draw!" << endl;
        exit(0);
    }

    int lineNumber = 0;
    //TessUnit *tessUnit = NULL;
    sphereUnit = new TessUnit;
    sphereUnit->noOfTessUnitVertices = 0;
    sphereUnit->ambientColor = color4(0.2, 0.2, 0.2, 1.0);
    sphereUnit->specularColor = color4(1.0, 0.84, 0.0, 1.0);
    sphereUnit->diffuseColor = color4(1.0, 0.84, 0.0, 1.0);
    sphereUnit->shininessCoeff = 125.0;
    int vertices = 0;
    while (getline(fileStream, nextLine)) {

        nextLine = trim(nextLine);

        // if blank line, then continue
        if (nextLine.length() == 0) {
            continue;
        } else {
            // initiate tess unit
            myStream.clear();
            myStream.str(nextLine);
            if (lineNumber == 0) {
                //tessUnit = NULL;
                //tessUnit = new TessUnit;
                vertices = 0;
                myStream >> vertices;
                sphereUnit->noOfTessUnitVertices += vertices;

            } else {
                float x, y, z;
                myStream >> x;
                myStream >> y;
                myStream >> z;
                sphereUnit->tessVertices.push_back(point3(x, y, z));
                sphereUnit->tessVertexColors.push_back(color3(1.0, 0.84, 0));
            }
            lineNumber++;
            if (lineNumber > vertices) {
                lineNumber = 0;
            }
        }
    }

}

