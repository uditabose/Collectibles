
#include <iostream>
#include <fstream>
#include <sstream>
#include <math.h>
#include <vector>
#include <string>

#include "Angel-yjc.h"

using namespace std;

/*------------- Variable declarations ----------------------*/

typedef Angel::vec3  color3;
typedef Angel::vec3  point3;

GLuint Angel::InitShader(const char* vShaderFile, const char* fShaderFile);

GLuint program;       /* shader program object id */
GLuint sphere_buffer;   /* vertex buffer object id for cube */
GLuint floor_buffer;  /* vertex buffer object id for floor */
GLuint xaxis_buffer;
GLuint yaxis_buffer;
GLuint zaxis_buffer;

// Projection transformation parameters

GLfloat  fovy = 45.0;  // Field-of-view in Y direction angle (in degrees)
GLfloat  aspect;       // Viewport aspect ratio
GLfloat  zNear = 0.5, zFar = 10.0; // near and far field of view

GLfloat angle = 0.0; // rotation angle in degrees
GLfloat angleIncreament = 0.006; // angle by which rotation increases
vec4 rollingVtx1(-4.0, 1.0, 4.0, 1.0);
vec4 rollingVtx2(3.0, 1.0, -4.0, 1.0);
vec4 rollingVtx3(-3.0, 1.0, -3.0, 1.0);
float rollingSlope1 = (float)((float)(rollingVtx2.z - rollingVtx1.z)
							/(float)(rollingVtx2.x - rollingVtx1.x));
float rollingSlope2 = (float)((float)(rollingVtx3.z - rollingVtx2.z)
							/(float)(rollingVtx3.x - rollingVtx2.x));
float rollingSlope3 = (float)((float)(rollingVtx1.z - rollingVtx3.z)
							/(float)(rollingVtx1.x - rollingVtx3.x));
int rollingLeg = 0;

vec4 rollingAxis(rollingVtx1.x, rollingVtx1.y, rollingVtx1.z, 0);
vec4 rolledPosition = rollingVtx1;
mat4 init_rotation(1.0);
mat4 accumulated_rotation = init_rotation;

vec4 init_eye(7.0, 3.0, -10.0, 1.0); // initial viewer position
vec4 eye = init_eye;               // current viewer position

int rollingBeginFlag = 0;
int animationFlag = 0; // 1: animation; 0: non-animation. Toggled by key 'a' or 'A'
int smoothRoll = 0; // whether to include accumulation rotation

struct TessUnit {
    int noOfTessUnitVertices; /* number of vertices per tesselation unit */
    vector<point3> tessVertices;
    vector<color3> tessVertexColors;
};

int noOfTessalationUnits = 0; /* number of tesselation units */
vector<TessUnit*> tessalationPolygons;

TessUnit *sphereUnit;
TessUnit *floorUnit;
TessUnit *xAxisUnit;
TessUnit *yAxisUnit;
TessUnit *zAxisUnit;

int glWindowHeight = 520;   /* window height */
int glWindowWidth = 520;    /* window width */
int glWindowCenterX = (glWindowWidth/2), glWindowCenterY = (glWindowHeight/2);  /* window centers */
color3 glWindowColor(0.529, 0.807, 0.92); //0.0

/*------------- utility methods -------------*/

/* reads the input file and populates the list with given data */
void prepareInput();

/* trims a string */
string trim(string strToTrim);

/* prints the sphere units */
void printTessUnits();

/*------------------ object setup functions -------------------------*/
void sphere();
void floor();
void axis();

void init();
void drawObject(GLuint buffer, int num_vertices);
int bufferDataSize(TessUnit *tessUnit);
int bufferColorSize(TessUnit *tessUnit);

/* ----------------- gl callback methods -----------------------------*/
void display();
void idle();
void reshape(int width, int height);
void keyboard(unsigned char key, int x, int y);
void mouse(int button, int state, int x, int y);
void contextMenu(int id);

/*------------- Setting up sphere, and the scene methods -------------*/

int main(int argc, char **argv){

    int status;

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE | GLUT_DEPTH);
    glutInitWindowSize(glWindowWidth, glWindowHeight);

    glutCreateWindow("Rolling Sphere");

    /* Call glewInit() and error checking */
    status = glewInit();
    if (GLEW_OK != status)
    {
        printf("Error: glewInit failed: %s\n", (char*) glewGetErrorString(status));
        exit(1);
    }
    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboard);
    glutMouseFunc(mouse);

    glutCreateMenu (contextMenu);
    glutAddMenuEntry("Default View Point", 1) ;
    glutAddMenuEntry("Quit", 2) ;
    glutAttachMenu (GLUT_LEFT_BUTTON);

    init();
    glutMainLoop();
    return 0;
}

/*------------- gl callback functions ---------------*/

void display(){
    GLuint  model_view;  // model-view matrix uniform shader variable location
    GLuint  projection;  // projection matrix uniform shader variable location

    glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

    glUseProgram(program); // Use the shader program

    model_view = glGetUniformLocation(program, "model_view" );
    projection = glGetUniformLocation(program, "projection" );

    /*---  Set up and pass on Projection matrix to the shader ---*/
    mat4  p = Perspective(fovy, aspect, zNear, zFar);
    glUniformMatrix4fv(projection, 1, GL_TRUE, p); // GL_TRUE: matrix is row-major

    /*---  Set up and pass on Model-View matrix to the shader ---*/
    // eye is a global variable of vec4 set to init_eye and updated by keyboard()
    vec4    at(0.0, 0.0, 0.0, 1.0);
    vec4    up(0.0, 1.0, 0.0, 0.0);

    mat4  mv = LookAt(eye, at, up);

    // drawing axises
	mv = Translate(0, 0.1, 0) * LookAt(eye, at, up);
    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    drawObject(xaxis_buffer, xAxisUnit->noOfTessUnitVertices);  // draw the axis
    drawObject(yaxis_buffer, yAxisUnit->noOfTessUnitVertices);  // draw the axis
    drawObject(zaxis_buffer, zAxisUnit->noOfTessUnitVertices);  // draw the axis


    mv = Translate(0, 0, 0) * LookAt(eye, at, up) * Scale (1.1, 1.1, 1.1) * Rotate(5.0, 0.0, 2.0, 0.0);
    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    drawObject(floor_buffer, floorUnit->noOfTessUnitVertices);  // draw the floor

    //printf("x = %f, y = %f, z = %f\n", rolledPosition.x, rolledPosition.y, rolledPosition.z);
    // draw the circle
	//printf("x = %f, y = %f, z = %f\n", rollingAxis.x, rollingAxis.y, rollingAxis.z);

    mv = LookAt(eye, at, up)
    * Rotate(angle, rollingAxis.x, rollingAxis.y, rolledPosition.z)
    * accumulated_rotation
    * Translate(rolledPosition.x, rolledPosition.y, rolledPosition.z)
	;

    glUniformMatrix4fv(model_view, 1, GL_TRUE, mv); // GL_TRUE: matrix is row-major
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    drawObject(sphere_buffer, sphereUnit->noOfTessUnitVertices);  // draw the axis

    glutSwapBuffers();
}

void reshape(int width, int height){
    glViewport(0, 0, width, height);
    aspect = (GLfloat) width  / (GLfloat) height;
    glutPostRedisplay();
}

void idle(){

    // accumulate rotation if smooth rolling is enabled
    GLfloat oldAngle = angle;
    angle += angleIncreament;
    bool isChangeDir = false;
	// determine the rotation axis

	float cSlope = 0.0;
	if(rollingLeg == 0) {
		// along A to B
		rollingAxis = rollingVtx2 - rollingVtx1;
		cSlope = rollingSlope1;
	} else if(rollingLeg == 1){
		// along A to B
		rollingAxis = rollingVtx3 - rollingVtx2;
		cSlope = rollingSlope2;
	} else {
		// along C to A
		rollingAxis = rollingVtx1 - rollingVtx3;
		cSlope = rollingSlope3;
	}
	// normal vector at rolling position with the plane
	vec4 up(0.0, 1.0, 0.0, 0.0);
	rollingAxis = normalize(cross(rollingAxis, up));

	// calculating the distance traversed
	float linearTranslation = angle * (((float)(2 * M_PI * 1))/(float)360);
	float xTravers = 0.0, zTravers = 0.0;
	xTravers = linearTranslation/sqrt(1 + pow(cSlope, 2.0));
	zTravers = abs((linearTranslation * cSlope)/sqrt(1 + pow(cSlope, 2.0)));

    if(rollingLeg == 0) {
        if(rolledPosition.x > rollingVtx2.x) {
			rollingLeg = 1;
            isChangeDir = true;
        } else {
			rolledPosition.x += xTravers;
			rolledPosition.z -= zTravers;
		}
    } else if(rollingLeg == 1) {

        if(rolledPosition.x < rollingVtx3.x) {
			rollingLeg = 2;
            isChangeDir = true;
        } else {
			rolledPosition.x -= xTravers;
			rolledPosition.z += zTravers;
		}
    } else if (rollingLeg == 2) {
        if(rolledPosition.x < rollingVtx1.x) {
			rollingLeg = 0;
            isChangeDir = true;

        } else {
			rolledPosition.x -= xTravers;
			rolledPosition.z += zTravers;
		}
    }

    if(isChangeDir) {
        angle = angleIncreament;
        if(smoothRoll == 1) {
            accumulated_rotation = Rotate(oldAngle, rolledPosition.x, rolledPosition.y, rolledPosition.z);
        } else {
            accumulated_rotation = init_rotation;
        }
        cout << accumulated_rotation << endl;
    }

    rollingAxis.x = 1.0;
    rollingAxis.y = 1.0;
    rollingAxis.z = 1.0;

    glutPostRedisplay();

}

void keyboard(unsigned char key, int x, int y){
    switch(key) {
	case 'q': case 'Q':
	    exit( EXIT_SUCCESS );
	    break;

    case 'X': eye[0] += 1.0; break;
	case 'x': eye[0] -= 1.0; break;
    case 'Y': eye[1] += 1.0; break;
	case 'y': eye[1] -= 1.0; break;
    case 'Z': eye[2] += 1.0; break;
	case 'z': eye[2] -= 1.0; break;
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

void mouse(int button, int state, int x, int y){
    if(state == GLUT_DOWN) {
        cout << "x = " << x << " y = " << y << endl;
        if(button == GLUT_RIGHT_BUTTON && rollingBeginFlag == 1) {
            if(animationFlag == 1) {
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
    switch(id) {
        case 1 : eye = init_eye; break;
        case 2 : exit( EXIT_SUCCESS ); break;
    }
}


/*------------------ object setup functions -------------------------*/
void sphere(){
}

void floor(){
    floorUnit = new TessUnit;
    floorUnit->noOfTessUnitVertices = 6;

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

}

void axis(){
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

int bufferDataSize(TessUnit *tessUnit){
    return ((tessUnit->tessVertices).size()  * sizeof(point3));
}
int bufferColorSize(TessUnit *tessUnit){
    return ((tessUnit->tessVertexColors).size() * sizeof(color3));
}

void init(){

    // sphere data
    prepareInput();

    // floor data
    floor();

    // axis data
    axis();

    // debug
    //printTessUnits();

    int dataSize = 0, colorSize = 0;

    // drawing axis - x ----------------------------
    dataSize = bufferDataSize(xAxisUnit);
    colorSize = bufferColorSize(xAxisUnit);

    //cout << " dataSize = " << dataSize << " colorSize = " << colorSize << xAxisUnit->tessVertexColors[0] << endl;

    glGenBuffers(1, &xaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, xaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(xAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(xAxisUnit->tessVertexColors[0]));

    // drawing axis - y ----------------------------
    dataSize = bufferDataSize(yAxisUnit);
    colorSize = bufferColorSize(yAxisUnit);

    //cout << " dataSize = " << dataSize << " colorSize = " << colorSize << endl;

    glGenBuffers(1, &yaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, yaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(yAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(yAxisUnit->tessVertexColors[0]));

    // drawing axis - z ----------------------------
    dataSize = bufferDataSize(zAxisUnit);
    colorSize = bufferColorSize(zAxisUnit);

    //cout << " dataSize = " << dataSize << " colorSize = " << colorSize << endl;

    glGenBuffers(1, &zaxis_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, zaxis_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(zAxisUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(zAxisUnit->tessVertexColors[0]));


    // drawing floor ---------------------------------
    dataSize = bufferDataSize(floorUnit);
    colorSize = bufferColorSize(floorUnit);

    cout << " dataSize = " << dataSize << " colorSize = " << colorSize << endl;

    glGenBuffers(1, &floor_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, floor_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(floorUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(floorUnit->tessVertexColors[0]));

    // drawing sphere ---------------------------------
    dataSize = bufferDataSize(sphereUnit);
    colorSize = bufferColorSize(sphereUnit);

    //cout << " dataSize = " << dataSize << " colorSize = " << colorSize << endl;

    glGenBuffers(1, &sphere_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, sphere_buffer);
    glBufferData(GL_ARRAY_BUFFER, dataSize + colorSize, NULL, GL_STATIC_DRAW);

    glBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, &(sphereUnit->tessVertices[0]));
    glBufferSubData(GL_ARRAY_BUFFER, dataSize, colorSize, &(sphereUnit->tessVertexColors[0]));

    // Load shaders and create a shader program (to be used in display())
    program = InitShader("/home/papa/studyspace/CS6533/Tutorials/Rotate-Cube-New/vshader42.glsl",
            "/home/papa/studyspace/CS6533/Tutorials/Rotate-Cube-New/fshader42.glsl");

    glEnable( GL_DEPTH_TEST );
    glClearColor( 0.529, 0.807, 0.92, 0.0 );
    glLineWidth(2.0);

}

void drawObject(GLuint buffer, int num_vertices){
    //--- Activate the vertex buffer object to be drawn ---//
    glBindBuffer(GL_ARRAY_BUFFER, buffer);

    /*----- Set up vertex attribute arrays for each vertex attribute -----*/
    GLuint vPosition = glGetAttribLocation(program, "vPosition");
    glEnableVertexAttribArray(vPosition);
    glVertexAttribPointer(vPosition, 3, GL_FLOAT, GL_FALSE, 0,
			  BUFFER_OFFSET(0) );

    GLuint vColor = glGetAttribLocation(program, "vColor");
    glEnableVertexAttribArray(vColor);
    glVertexAttribPointer(vColor, 3, GL_FLOAT, GL_FALSE, 0,
			  BUFFER_OFFSET(sizeof(point3) * num_vertices) );
      // the offset is the (total) size of the previous vertex attribute array(s)

    /* Draw a sequence of geometric objs (triangles) from the vertex buffer
       (using the attributes specified in each enabled vertex attribute array) */
    if(num_vertices < 3) {
        glDrawArrays(GL_LINES, 0, num_vertices);
    } else {
        glDrawArrays(GL_TRIANGLES, 0, num_vertices);
    }

    /*--- Disable each vertex attribute array being enabled ---*/
    glDisableVertexAttribArray(vPosition);
    glDisableVertexAttribArray(vColor);
}

/*------------- Utility functions -------------------*/
string trim(string strToTrim)
{
    string trimmedString(strToTrim);

    size_t nonWhiteCharPos = trimmedString.find_last_not_of(' ');

    if(nonWhiteCharPos != string::npos) {
        // Right trim
        trimmedString.erase(nonWhiteCharPos + 1);
        nonWhiteCharPos = trimmedString.find_first_not_of(' ');

        // Left trim
        if(nonWhiteCharPos != string::npos) {
            trimmedString.erase(0, nonWhiteCharPos);
        }
    }  else {
        // A string with just whitespace
        trimmedString.erase(trimmedString.begin(), trimmedString.end());
    }

    return trimmedString;
}

void printTessUnits(){
    int counter = 0;
    cout << "tess unit number : " << sphereUnit->noOfTessUnitVertices << endl;
    while(counter < noOfTessalationUnits) {
        point3 point = sphereUnit->tessVertices[counter];
        cout <<  counter + 1 << endl;

        cout << point.x << ", "
            << point.y << ", "
            << point.z << endl;
        counter++;
    }
}

void prepareInput() {
    string filePath;
    //cout << "Please enter a file path" << endl;
    //getline(cin, filePath);

    //debug
    filePath = "/home/papa/studyspace/CS6533/HW/Sphere/src/sphere.128";

    std::ifstream fileStream(filePath.c_str());

    string nextLine;
    getline(fileStream, nextLine);
    stringstream myStream(nextLine);

    myStream >> noOfTessalationUnits;

    cout << "noOfTessalationUnits :: " << noOfTessalationUnits << endl;

    if(noOfTessalationUnits == 0) {
        cout << "Nothing to draw!" << endl;
        exit(0);
    }

    int lineNumber = 0;
    //TessUnit *tessUnit = NULL;
    sphereUnit = new TessUnit;
    sphereUnit->noOfTessUnitVertices = 0;
    int vertices = 0;
    while(getline(fileStream, nextLine)) {

        nextLine = trim(nextLine);

        // if blank line, then continue
        if(nextLine.length() == 0) {
            continue;
        } else {
            // initiate tess unit
            myStream.clear();
            myStream.str(nextLine);
            if(lineNumber == 0){
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
            if(lineNumber > vertices) {
                lineNumber = 0;
                //tessalationPolygons.push_back(tessUnit);
            }
        }
    }

}
