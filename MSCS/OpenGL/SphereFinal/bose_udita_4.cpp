
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

/* Tessalation units */
struct TessUnit {
    int noOfTessUnitVertices; /* number of vertices per tesselation unit */
    vector<point3> tessVertices;
    vector<color3> tessVertexColors;
    vector<point3> tessVertexNormals;
    vector<vec2> texCoord;
    vec4 planeNormal;
    color4 ambientColor;
    color4 diffuseColor;
    color4 specularColor;
    float shininessCoeff;
};

struct LightUnit {
    color4 ambient;
    color4 diffuse;
    color4 specular;
    vec4 lightPosition;
    vec4 spotPosition;
    float spotCutOffAngle;
    float spotExponent;

};

struct FireWork {
    point4 initPos;
    vector<color4> color;
    vector<vec3> velocity;
};
/* ----------------GL Variables----------------------*/
GLuint Angel::InitShader(const char* vShaderFile, const char* fShaderFile);
GLuint program; /* shader program object id */
GLuint simpleProgram;
GLuint lightingProgram;
GLuint shadowProgram;
GLuint fwProgram;
GLuint sphere_buffer; /* vertex buffer object id for cube */
GLuint floor_buffer; /* vertex buffer object id for floor */
GLuint xaxis_buffer;
GLuint yaxis_buffer;
GLuint zaxis_buffer;
GLuint shadow_buffer;
GLuint floor_texture_buffer;
GLuint sphere_texture_buffer;
GLuint fw_buffer;

// Projection transformation parameters
GLfloat fovy = 45.0; // Field-of-view in Y direction angle (in degrees)
GLfloat aspect; // Viewport aspect ratio
GLfloat zNear = 0.5, zFar = 20.0; // near and far field of view
GLfloat angle = 0.0; // rotation angle in degrees
GLfloat angleIncreament = 0.5; // angle by which rotation increases

/*----------------- The objects to be drawn ----------------*/
TessUnit *sphereUnit;
TessUnit *floorUnit;
TessUnit *xAxisUnit;
TessUnit *yAxisUnit;
TessUnit *zAxisUnit;

/*------------------ Light set up --------------------------*/
LightUnit *shadowLight;
LightUnit *globalAmbientLight;
LightUnit *distantLight;
LightUnit *spotLight;
LightUnit *pointLight;

/* ------------ firework -----------------------------------*/
FireWork *fireWorks;
float startTime = 0.0;
int currentCycleNumber = 0;
int maxCycleNumber = 1000; // modify this

/* ------------- Variable related to rotation ------------- */
int rollingLeg = 0;
vec4 rollingVtx1(-4.0, 1.0, 4.0, 1.0);
vec4 rollingVtx2(3.0, 1.0, -4.0, 1.0);
vec4 rollingVtx3(-3.0, 1.0, -3.0, 1.0);
vec4 rollingAxis = rollingVtx1;
vec4 rollingStart = rollingVtx1;
vec4 rolledPosition(0.0, 0.0, 0.0, 0.0);
mat4 init_rotation(1.0);
mat4 accumulated_rotation = init_rotation;

/* ------------------------- Eye variables -------------------*/
vec4 init_eye(7.0, 3.0, -10.0, 1.0); // initial viewer position
vec4 eye = init_eye; // current viewer position

/*---------------- Flags ------------------------------------*/
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
int fogType = 0; // 0 = none, 1 = linear, 2 = exponential, 3 = square exponential
int blendedShadow = 0; // 0 = no, 1 = yes
int enableFloorTexture = 0; // 0 = no, 1 = yes
int sphereTextureType = 0; // 0 = none, 1 = vertical, 2 = slanted
int enableFirework = 0; // 0 = no, 1 = yes

/* --------------Window related properties--------------*/
int glWindowHeight = 600; /* window height */
int glWindowWidth = 600; /* window width */
//int glWindowCenterX = (glWindowWidth / 2), glWindowCenterY = (glWindowHeight / 2); /* window centers */
color3 glWindowColor(0.529, 0.807, 0.92); //0.0

/* ------------- Texture set up --------------------------------------*/

#define ImageWidth  128
#define ImageHeight 128
GLubyte Image[ImageHeight][ImageWidth][4];

#define	stripeImageWidth 32
GLubyte stripeImage[4 * stripeImageWidth];

/* ----------------- gl callback methods -----------------------------*/
void display();
void idle();
void reshape(int width, int height);
void keyboard(unsigned char key, int x, int y);
void mouse(int button, int state, int x, int y);
void contextMenu(int id);

/*------------------ object setup functions -------------------------*/
void init();
void initLight();
void floor();
void axis();
void firework();
void image_set_up(void);
void drawObject(GLuint buffer, int num_vertices);
void drawShadow(mat4 mv, mat4 lookAt, mat4 p);
void drawFirework(mat4 lookAt);
void prepareShaderForDistantSource(TessUnit *tessUnit, mat4 mv);
void prepareShaderForNearSource(TessUnit *tessUnit, mat4 mv, mat4 lmv);
void prepareSphereNormal();
void prepareSphereTextureCoord();
void prepareBufferForDisplay();
void loadShader();
int getDataSize(TessUnit *tessUnit);
int getColorSize(TessUnit *tessUnit);
int getNormalSize(TessUnit *tessUnit);
int getTextureCoordSize(TessUnit *tessUnit);
/*------------- utility methods -------------*/

/* reads the input file and populates the list with given data */
void prepareInput();

/* trims a string */
string trim(string strToTrim);

/* prints the sphere units */
void printTessUnits(TessUnit *tessUnit);

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

    int fogMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("No Fog", 13);
    glutAddMenuEntry("Linear", 14);
    glutAddMenuEntry("Exponential", 15);
    glutAddMenuEntry("Exponential Square", 16);

    int blendedShadowMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("No", 17);
    glutAddMenuEntry("Yes", 18);
    
    int textureFloorMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("No", 19);
    glutAddMenuEntry("Yes", 20);
    
    int fireworkMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("No", 21);
    glutAddMenuEntry("Yes", 22);
    
    int textureSphereMenu = glutCreateMenu(contextMenu);
    glutAddMenuEntry("No", 23);
    glutAddMenuEntry("Yes", 24);

    glutCreateMenu(contextMenu);
    glutAddMenuEntry("Default View Point", 1);
    glutAddMenuEntry("Quit", 2);
    glutAddSubMenu("Shadow", shadowMenu);
    glutAddSubMenu("Enable Light", lightingMenu);
    glutAddSubMenu("Enable Shading", shadingMenu);
    glutAddSubMenu("Lighting", lightMenu);
    glutAddSubMenu("Wireframe", viewMenu);
    glutAddSubMenu("Fog", fogMenu);
    glutAddSubMenu("Blending Shadow", blendedShadowMenu);
    glutAddSubMenu("Texture Mapped Floor", textureFloorMenu);
    glutAddSubMenu("Texture Mapped Sphere", textureSphereMenu);
    glutAddSubMenu("Firework", fireworkMenu);

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

    // set up lookup matrix
    mat4 lookAt = LookAt(eye, at, up);

    // drawing axis
    mat4 mv = Translate(0, 0.1, 0) * lookAt;
    mat4 fmv = p * mv;

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    drawObject(xaxis_buffer, xAxisUnit->noOfTessUnitVertices); // draw the axis
    drawObject(yaxis_buffer, yAxisUnit->noOfTessUnitVertices); // draw the axis
    drawObject(zaxis_buffer, zAxisUnit->noOfTessUnitVertices); // draw the axis

    glActiveTexture( GL_TEXTURE0 );
    glBindTexture(GL_TEXTURE_2D, floor_texture_buffer);
    glUniform1i( glGetUniformLocation(program, "texture"), 0 );
    
    // Pass on the value of enableFloorTexture to the fragment shader
    glUniform1i( glGetUniformLocation(program, "enableFloorTexture"), 
                enableFloorTexture);
    glUniform1i( glGetUniformLocation(program, "sphereTexture"), sphereTextureType);
    glUniform1i(glGetUniformLocation(program, "fogType"),
            fogType);
    
    // drawing the floor
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, ImageWidth, ImageHeight, 
                0, GL_RGBA, GL_UNSIGNED_BYTE, Image);
    glUniform1i(glGetUniformLocation(program, "objectType"),
            1);
    
    mv = lookAt * Scale(1.1, 1.1, 1.1) * Rotate(5.0, 0.0, 2.0, 0.0);

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major

    if (enableLighting == 1) {
        if (lightType == 1) {
            // distant light source
            prepareShaderForDistantSource(floorUnit, mv);
        } else {
            // point or spot light source
            prepareShaderForNearSource(floorUnit, mv, lookAt);
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

    mv = lookAt
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
    
    glBindTexture(GL_TEXTURE_1D, sphere_texture_buffer);
    
    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    glTexImage1D(GL_TEXTURE_1D, 0, GL_RGBA, stripeImageWidth, 0, 
        GL_RGBA, GL_UNSIGNED_BYTE, stripeImage);

    glUniform1i( glGetUniformLocation(program, "stexture"), 0 );
    glUniform1i(glGetUniformLocation(program, "objectType"), 2);
    glUniform1i( glGetUniformLocation(program, "enableFloorTexture"), 0);

    if (enableLighting == 1) {
        if (lightType == 1) {
            // distant light source
            prepareShaderForDistantSource(sphereUnit, mv);
        } else {
            // point or spot light source
            prepareShaderForNearSource(sphereUnit, mv, lookAt);
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
        if (blendedShadow == 1) {
            // going to blend the shadow
            glDepthMask(GL_FALSE);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }
        drawShadow(nmv, lookAt, p);
        if (blendedShadow == 1) {
            // going to reset the blend mode
            glDisable(GL_BLEND);

            glDepthMask(GL_TRUE);
            glColorMask(GL_FALSE, GL_FALSE, GL_FALSE, GL_FALSE);
            drawShadow(nmv, lookAt, p);
            glColorMask(GL_TRUE, GL_TRUE, GL_TRUE, GL_TRUE);

        }
    }
    
    // drawing firework
    drawFirework(fmv);

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
        case 'v': case 'V':
            sphereTextureType = 1;
            prepareSphereTextureCoord();
            break;
        case 's': case 'S':
            sphereTextureType = 2;
            prepareSphereTextureCoord();
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
        case 5: enableLighting = 1;
            lightType = 1;
            break;
        case 6: enableLighting = 0;
            lightType = 0;
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
        case 13: fogType = 0;
            break;
        case 14: fogType = 1;
            break;
        case 15: fogType = 2;
            break;
        case 16: fogType = 3;
            break;
        case 17: blendedShadow = 0;
            break;
        case 18: blendedShadow = 1;
            break;
        case 19: enableFloorTexture = 0;
            break;
        case 20: enableFloorTexture = 1;
            break;
        case 21: enableFirework = 0;
            break;
        case 22: enableFirework = 1;
            break;
        case 23: 
            sphereTextureType = 0; 
            break;
        case 24: sphereTextureType = 1;
            prepareSphereTextureCoord();
            break;
    }
}

/*------------------ object setup functions -------------------------*/
void init() {

    // sphere data
    prepareInput();

    // floor data
    floor();

    // axis data
    axis();

    // light set up
    initLight();
    
    // texture set up
    image_set_up();
    
    // fireworks
    firework();

    int dataSize = 0, colorSize = 0;

    // drawing axis - x ----------------------------
    dataSize = getDataSize(xAxisUnit);
    colorSize = getColorSize(xAxisUnit);

    glGenBuffers(1, &xaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, xaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(xAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(xAxisUnit->tessVertexColors[0]));

    // drawing axis - y ----------------------------
    dataSize = getDataSize(yAxisUnit);
    colorSize = getColorSize(yAxisUnit);

    glGenBuffers(1, &yaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, yaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(yAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(yAxisUnit->tessVertexColors[0]));

    // drawing axis - z ----------------------------
    dataSize = getDataSize(zAxisUnit);
    colorSize = getColorSize(zAxisUnit);

    glGenBuffers(1, &zaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, zaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(zAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(zAxisUnit->tessVertexColors[0]));
    
    // preparing floor texture data
    /*--- Create and Initialize a texture object ---*/
   glGenTextures(1, &floor_texture_buffer);
   glGenTextures(1, &sphere_texture_buffer);
   

    simpleProgram = InitShader("vshader42.glsl", 
            "fshader42.glsl");
    lightingProgram = InitShader("lshader42.glsl",
            "fshader42.glsl");
    shadowProgram = InitShader("sshader42.glsl",
            "fshader42.glsl");
    fwProgram = InitShader("fwvshader.glsl",
            "fwfshader.glsl");

    glEnable(GL_DEPTH_TEST);
    glClearColor(0.529, 0.807, 0.92, 0.0);
    glLineWidth(1.0);
}

void prepareInput() {
    string filePath;
    cout << "Please enter a file path" << endl;
    getline(cin, filePath);

    //debug
    //filePath = "sphere.1024";

    std::ifstream fileStream(filePath.c_str());

    string nextLine;
    getline(fileStream, nextLine);
    stringstream myStream(nextLine);

    int noOfTessalationUnits = 0;

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

float vertTexCoord(point3 vertex) {
    return vertex.x * 2.5;
}

float slantTexCoord(point3 vertex) {
    return vertex.x + vertex.y + vertex.z;
}

void prepareSphereTextureCoord() {
    if(sphereTextureType == 0) {
        return;
    }
    sphereUnit->texCoord.clear();
    float (*calc)(point3);
    
    if(sphereTextureType == 1) {
        calc = vertTexCoord;  
    } else {
        calc = slantTexCoord;
    }
    for (int i = 0; i < sphereUnit->noOfTessUnitVertices; i++) {
        sphereUnit->texCoord.push_back(vec2(calc(sphereUnit->tessVertices[i]), 0.0));
    }
}

void initLight() {

    shadowLight = new LightUnit();
    shadowLight->lightPosition = point4(-14.0, 12.0, -3.0, 1.0);

    globalAmbientLight = new LightUnit();
    globalAmbientLight->ambient = color4(1.0, 1.0, 1.0, 1.0);

    distantLight = new LightUnit();
    distantLight->ambient = color4(0.0, 0.0, 0.0, 1.0);
    distantLight->diffuse = color4(1.0, 1.0, 1.0, 1.0);
    distantLight->specular = color4(1.0, 1.0, 1.0, 1.0);
    distantLight->lightPosition = point4(0.0, 0.0, 1.0, 0.0);

    pointLight = new LightUnit();
    pointLight->lightPosition = point4(-14.0, 12.0, -3.0, 1.0);
    pointLight->ambient = color4(0.0, 0.0, 0.0, 1.0);
    pointLight->diffuse = color4(1.0, 1.0, 1.0, 1.0);
    pointLight->specular = color4(1.0, 1.0, 1.0, 1.0);

    spotLight = new LightUnit();
    spotLight->lightPosition = point4(-14.0, 12.0, -3.0, 1.0);
    spotLight->ambient = color4(0.0, 0.0, 0.0, 1.0);
    spotLight->diffuse = color4(1.0, 0.0, 1.0, 1.0);
    spotLight->specular = color4(1.0, 1.0, 1.0, 1.0);
    spotLight->spotPosition = point4(-6.0, 0.0, -4.5, 1.0);
    spotLight->spotCutOffAngle = 20.0;
    spotLight->spotExponent = 5.0;
}

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
    
    // texture coordinates
    floorUnit->texCoord.push_back(vec2(0.0, 0.0));
    floorUnit->texCoord.push_back(vec2(0.0, 1.0));
    floorUnit->texCoord.push_back(vec2(1.0, 1.0));
    
    floorUnit->texCoord.push_back(vec2(1.0, 1.0));
    floorUnit->texCoord.push_back(vec2(1.0, 0.0));
    floorUnit->texCoord.push_back(vec2(0.0, 0.0));
 
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

void firework() {
    int i = 0;
    startTime = glutGet(GLUT_ELAPSED_TIME);
    fireWorks = new FireWork();
    fireWorks->initPos = point4(0.0, 0.1, 0.0, 1.0);
    while(i++ < 300) {
        fireWorks->velocity.push_back(vec3(2.0*((rand()%256)/256.0-0.5), 
                1.2*2.0*((rand()%256)/256.0), 
                2.0*((rand()%256)/256.0-0.5)));
        
        fireWorks->color.push_back(color4((rand()%256)/256.0, 
                (rand()%256)/256.0, 
                (rand()%256)/256.0, 
                1.0));
    }
    
    cout << fireWorks->color[0] << endl;
    cout << fireWorks->color[10] << endl;

    glGenBuffers(1, &fw_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, fw_buffer);
    glBufferData(GL_ARRAY_BUFFER, (sizeof(vec3) + sizeof(color4)) * 300, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, sizeof(color4) * 300, &(fireWorks->color[0]));
    glBufferSubData(GL_ARRAY_BUFFER, sizeof(color4) * 300, sizeof(vec3) * 300, &(fireWorks->velocity[0]));
    
}

void image_set_up(void) {
    int i, j, c;

    /* --- Generate checkerboard image to the image array ---*/
    for (i = 0; i < ImageHeight; i++)
        for (j = 0; j < ImageWidth; j++) {
            c = (((i & 0x8) == 0) ^ ((j & 0x8) == 0));

            if (c == 1) /* white */ {
                c = 255;
                Image[i][j][0] = (GLubyte) c;
                Image[i][j][1] = (GLubyte) c;
                Image[i][j][2] = (GLubyte) c;
            } else /* green */ {
                Image[i][j][0] = (GLubyte) 0;
                Image[i][j][1] = (GLubyte) 150;
                Image[i][j][2] = (GLubyte) 0;
            }

            Image[i][j][3] = (GLubyte) 255;
        }

    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

    /*--- Generate 1D stripe image to array stripeImage[] ---*/
    for (j = 0; j < stripeImageWidth; j++) {
        /* When j <= 4, the color is (255, 0, 0),   i.e., red stripe/line.
           When j > 4,  the color is (255, 255, 0), i.e., yellow remaining texture
         */
        stripeImage[4 * j] = (GLubyte) 255;
        stripeImage[4 * j + 1] = (GLubyte) ((j > 4) ? 255 : 0);
        stripeImage[4 * j + 2] = (GLubyte) 0;
        stripeImage[4 * j + 3] = (GLubyte) 1;
    }

    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
    /*----------- End 1D stripe image ----------------*/
} 

void prepareBufferForDisplay() {
    int dataSize = 0, colorOrNormalSize = 0, textureSize = 0;

    // drawing floor ---------------------------------
    dataSize = getDataSize(floorUnit);
    textureSize = getTextureCoordSize(floorUnit); 
    if (enableLighting == 1) {
        colorOrNormalSize = getNormalSize(floorUnit);
    } else {
        colorOrNormalSize = getColorSize(floorUnit);
    }

    glGenBuffers(1, &floor_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, floor_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorOrNormalSize + textureSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(floorUnit->tessVertices[0]));
    if (enableLighting == 1) {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(floorUnit->tessVertexNormals[0]));
    } else {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(floorUnit->tessVertexColors[0]));
    }
    glBufferSubData(GL_ARRAY_BUFFER, dataSize + colorOrNormalSize, textureSize, &(floorUnit->texCoord[0]));

    // drawing sphere ---------------------------------
    dataSize = getDataSize(sphereUnit);
    if (enableLighting == 1) {
        colorOrNormalSize = getNormalSize(sphereUnit);
    } else {
        colorOrNormalSize = getColorSize(sphereUnit);
    }

    textureSize = getTextureCoordSize(sphereUnit);
    glGenBuffers(1, &sphere_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, sphere_buffer);
    
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorOrNormalSize + textureSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(sphereUnit->tessVertices[0]));
    if (enableLighting == 1) {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(sphereUnit->tessVertexNormals[0]));
    } else {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorOrNormalSize, &(sphereUnit->tessVertexColors[0]));
    }
    if (textureSize > 0) {
        glBufferSubData(GL_ARRAY_BUFFER, dataSize + colorOrNormalSize, textureSize, &(sphereUnit->texCoord[0]));
    }

    // shadow buffer -----------------
    glGenBuffers(1, &shadow_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, shadow_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize, NULL, GL_STATIC_DRAW);
    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(sphereUnit->tessVertices[0]));
}

void drawObject(GLuint buffer, int num_vertices) {
    //--- Activate the vertex buffer object to be drawn ---//
    glBindBuffer(GL_ARRAY_BUFFER, buffer);

    /*----- Set up vertex attribute arrays for each vertex attribute -----*/
    GLuint vPosition = glGetAttribLocation(program, "vPosition");
    glEnableVertexAttribArray(vPosition);
    glVertexAttribPointer(vPosition, 3, GL_FLOAT, GL_FALSE, 0,
            BUFFER_OFFSET(0));

    GLuint vColor = 0, vNormal = 0, vTexCoord = 0;

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

    vTexCoord = glGetAttribLocation( program, "vTexCoord" ); 
    glEnableVertexAttribArray( vTexCoord );
    glVertexAttribPointer(vTexCoord, 2, GL_FLOAT, GL_FALSE, 0,
            BUFFER_OFFSET( 2 * sizeof (point3) * num_vertices) ); 

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
    glDisableVertexAttribArray(vTexCoord);
    glDisableVertexAttribArray(vNormal);
}

void drawShadow(mat4 mv, mat4 lookAt, mat4 p) {

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
    glUniform4fv(sLight, 1, shadowLight->lightPosition);

    glBindBuffer(GL_ARRAY_BUFFER, shadow_buffer);

    /*----- Set up vertex attribute arrays for each vertex attribute -----*/
    GLuint vPosition = glGetAttribLocation(shadowProgram, "vPosition");
    glEnableVertexAttribArray(vPosition);
    glVertexAttribPointer(vPosition, 3, GL_FLOAT, GL_FALSE, 0,
            BUFFER_OFFSET(0));

    glPointSize(5.0);
    glDrawArrays(GL_TRIANGLES, 0, num_vertices);

    /*--- Disable each vertex attribute array being enabled ---*/
    glDisableVertexAttribArray(vPosition);

}

void drawFirework(mat4 lookAt) {
    if(enableFirework != 1) {
        // no firework
        startTime = glutGet(GLUT_ELAPSED_TIME);
        currentCycleNumber = 0; 
        return;
    }
    
    glUseProgram(fwProgram); // Use the shader program
    
    float elapsedTime = 0.0;
    if (currentCycleNumber++ >= maxCycleNumber) {
        // reset the cycle
        startTime = glutGet(GLUT_ELAPSED_TIME);
        currentCycleNumber = 0;  
    }
    
    //cout << "currentCycleNumber : " << currentCycleNumber << endl;
    
    elapsedTime = glutGet(GLUT_ELAPSED_TIME) - startTime;

    GLuint lA = glGetUniformLocation(fwProgram, "lookAt");
    
    glUniformMatrix4fv(lA, 1, GL_TRUE, lookAt);
    glUniform1f(glGetUniformLocation(fwProgram, "timeElapsed"),
            elapsedTime);
    glUniform4fv(glGetUniformLocation(fwProgram, "initPosition"),
            1, fireWorks->initPos);
    
     //--- Activate the vertex buffer object to be drawn ---//
    glBindBuffer(GL_ARRAY_BUFFER, fw_buffer);
    //
    /*----- Set up vertex attribute arrays for each vertex attribute -----*/
    GLuint vColor = glGetAttribLocation(fwProgram, "vColor");
    glEnableVertexAttribArray(vColor);
    glVertexAttribPointer(vColor, 4, GL_FLOAT, GL_FALSE, 0,
            BUFFER_OFFSET(0));
    
    GLuint vVelocity = glGetAttribLocation(fwProgram, "vVelocity");
    glEnableVertexAttribArray(vVelocity);
    glVertexAttribPointer(vVelocity, 3, GL_FLOAT, GL_FALSE, 0,
                BUFFER_OFFSET(300 * sizeof(color4)));
    
    glPolygonMode(GL_FRONT_AND_BACK, GL_POINT);
    glPointSize(3.0);
    glDrawArrays(GL_POINTS, 0, 300);
    
    glDisableVertexAttribArray(vColor);
    glDisableVertexAttribArray(vVelocity);
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

void prepareShaderForDistantSource(TessUnit *tessUnit, mat4 mv) {

    mat3 normal_matrix = NormalMatrix(mv, 1);

    glUniformMatrix3fv(glGetUniformLocation(program, "Normal_Matrix"),
            1, GL_TRUE, normal_matrix);
    // attenuation = 1

    color4 ambient_product = distantLight->ambient * tessUnit->ambientColor;
    color4 diffuse_product = distantLight->diffuse * tessUnit->diffuseColor;
    color4 specular_product = distantLight->specular * tessUnit->specularColor;

    color4 global_ambient_product = globalAmbientLight->ambient * tessUnit->ambientColor;

    float constAtt = 1.0;
    float linearAtt = 0.0;
    float quadAtt = 0.0;

    glUniform4fv(glGetUniformLocation(program, "DistantAmbientProduct"),
            1, ambient_product);
    glUniform4fv(glGetUniformLocation(program, "DistantDiffuseProduct"),
            1, diffuse_product);
    glUniform4fv(glGetUniformLocation(program, "DistantSpecularProduct"),
            1, specular_product);
    glUniform4fv(glGetUniformLocation(program, "GlobalAmbientProduct"),
            1, global_ambient_product);


    vec4 light_position_eyeFrame = distantLight->lightPosition;
    glUniform4fv(glGetUniformLocation(program, "DistantLightPosition"),
            1, light_position_eyeFrame);

    glUniform1f(glGetUniformLocation(program, "ConstAtt"),
            constAtt);
    glUniform1f(glGetUniformLocation(program, "LinearAtt"),
            linearAtt);
    glUniform1f(glGetUniformLocation(program, "QuadAtt"),
            quadAtt);

    glUniform1f(glGetUniformLocation(program, "Shininess"),
            tessUnit->shininessCoeff);

    glUniform1i(glGetUniformLocation(program, "DistanceLight"), 1);

    // spot and point source - just putting default values

    glUniform1i(glGetUniformLocation(program, "SpotLight"), 0);
    glUniform4fv(glGetUniformLocation(program, "NearLightPosition"),
            1, vec4(0.0, 0.0, 0.0, 0.0));
    glUniform4fv(glGetUniformLocation(program, "SpotlightPosition"),
            1, vec4(0.0, 0.0, 0.0, 0.0));
    glUniform1f(glGetUniformLocation(program, "SpotlightCutOffAngle"), 180.0);
    glUniform1f(glGetUniformLocation(program, "SpotlightExp"), 0.0);

}

void prepareShaderForNearSource(TessUnit *tessUnit, mat4 mv, mat4 lmv) {

    float constAtt = 2.0;
    float linearAtt = 0.01;
    float quadAtt = 0.001;

    mat3 normal_matrix = NormalMatrix(mv, 1);

    glUniformMatrix3fv(glGetUniformLocation(program, "Normal_Matrix"),
            1, GL_TRUE, normal_matrix);

    color4 ambient_product = distantLight->ambient * tessUnit->ambientColor;
    color4 diffuse_product = distantLight->diffuse * tessUnit->diffuseColor;
    color4 specular_product = distantLight->specular * tessUnit->specularColor;
    color4 global_ambient_product =
            globalAmbientLight->ambient * tessUnit->ambientColor;
    vec4 light_position_eyeFrame = distantLight->lightPosition;

    glUniform4fv(glGetUniformLocation(program, "DistantAmbientProduct"),
            1, ambient_product);
    glUniform4fv(glGetUniformLocation(program, "DistantDiffuseProduct"),
            1, diffuse_product);
    glUniform4fv(glGetUniformLocation(program, "DistantSpecularProduct"),
            1, specular_product);
    glUniform4fv(glGetUniformLocation(program, "GlobalAmbientProduct"),
            1, global_ambient_product);
    glUniform4fv(glGetUniformLocation(program, "DistantLightPosition"),
            1, light_position_eyeFrame);

    glUniform1f(glGetUniformLocation(program, "ConstAtt"),
            constAtt);
    glUniform1f(glGetUniformLocation(program, "LinearAtt"),
            linearAtt);
    glUniform1f(glGetUniformLocation(program, "QuadAtt"),
            quadAtt);

    glUniform1f(glGetUniformLocation(program, "Shininess"),
            tessUnit->shininessCoeff);

    glUniform1i(glGetUniformLocation(program, "DistanceLight"), 0);

    // spot and point source -
    LightUnit *nearLight = NULL;
    if (lightType == 2) {
        // spotlight
        glUniform1i(glGetUniformLocation(program, "SpotLight"), 1);
        nearLight = spotLight;
    } else if (lightType == 3) {
        // point source
        glUniform1i(glGetUniformLocation(program, "SpotLight"), 0);
        nearLight = pointLight;
    }

    light_position_eyeFrame = lmv * nearLight->lightPosition;
    glUniform4fv(glGetUniformLocation(program, "NearLightPosition"),
            1, light_position_eyeFrame);
    glUniform4fv(glGetUniformLocation(program, "SpotlightPosition"),
            1, lmv * nearLight->spotPosition);
    glUniform1f(glGetUniformLocation(program, "SpotlightCutOffAngle"),
            nearLight->spotCutOffAngle);
    glUniform1f(glGetUniformLocation(program, "SpotlightExp"),
            nearLight->spotExponent);

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

int getDataSize(TessUnit *tessUnit) {
    return ((tessUnit->tessVertices).size() * sizeof (point3));
}

int getColorSize(TessUnit *tessUnit) {
    return ((tessUnit->tessVertexColors).size() * sizeof (color3));
}

int getNormalSize(TessUnit *tessUnit) {
    return ((tessUnit->tessVertexNormals).size() * sizeof (point3));
}

int getTextureCoordSize(TessUnit *tessUnit) {
    return ((tessUnit->texCoord).size() * sizeof (vec2));
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

