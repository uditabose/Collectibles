/**
* CS6533 - Assignment 1
* Student name : Udita Bose
* Poly ID : 0527963
* Email id : ub298@nyu.edu
*/


#define WINDOWS     0 /* Set to 1 for Windows, 0 else */
#define UNIX_LINUX  1 /* Set to 1 for Unix/Linux, 0 else */

#if WINDOWS
#include <windows.h>
#include <GL/glut.h>
#endif
#if UNIX_LINUX
#include <GL/gl.h>
#include <GL/glu.h>
#include <GL/glut.h>
#endif

#include <iostream>
#include <fstream>
#include <sstream>
#include <math.h>
#include <list>
#include <string>

using namespace std;

/**
* This struct represents a circle
* alongwith the scaling factor
*/
struct CIRCLE
{
    int centerX;
    int centerY;
    int radius;
    float scalingFactor;
};

list<CIRCLE*> circles;  /* the circles to be drawn */

int frameNumber = 1;    /* current frame number */
int maxFrameNumber = 1; /* maximum number of frame, after a cycle of max
                                    frame numbers ewset the radius */
int numberOfCircles = 0;    /* number of circles to be drawn */
int glWindowHeight = 600;   /* window height */
int glWindowWidth = 600;    /* window width */
int glWindowCenterX = (glWindowWidth/2), glWindowCenterY = (glWindowHeight/2);  /* window centers */


/**
* display callback, called on every frame refresh
*/
void display(void);
/**
* method for common initialization
*/
void myinit(void);
/**
* checks for frame numbers
*/
void idle(void);

/**
* implementation of bresenhem algorithm, calculates
* the differential values for the coordinate choices
*/
void draw_circle(int x, int y, int r);

/**
* drwas the 8 points based on the differential calculations
*/

void circlePoint(int x, int y);

/**
* reads the input file and populates the list with given data
*/
void prepareInput();

/**
* computes the scaling factor based on circle and screen properties
*/
float computeScalingFactor(int x, int y, int r);

int main(int argc, char **argv)
{
    // Read user inputs
	// Mode 'C' - Part (C) of the question, draws one static circle based on user input
	// Mode 'D' - Part (D) of the question, drwas as many static circles as provided in the input file
	// Mode 'E' - Part (E) of the question, drwas as many circles as provided in the input file,
					// and supports animation
    string modeOfOp;
    cout << "Please enter an option, valid options are C, D and E " << endl;
    getline(cin, modeOfOp);

    maxFrameNumber = 1;
    frameNumber = maxFrameNumber;

	// We need the idle method only in case of animation, i.e Part (E)
    bool isIdleRequired = false;

    if(modeOfOp == "C")
    {
        cout << "Please enter 3 integer values " << endl;
        int x, y, r = 0;
        cin >> x >> y >> r;
        CIRCLE *c = new CIRCLE;
        c->centerX = x;
        c->centerY = y;
        c->radius = r;
        c->scalingFactor = computeScalingFactor(x, y, r);
        circles.push_back(c);
        numberOfCircles = 1;

    }
    else if(modeOfOp == "D" || modeOfOp == "E")
    {
        // for D and E we need to read the file
        prepareInput();

        maxFrameNumber = 1;
        frameNumber = maxFrameNumber;

        if (modeOfOp == "E")
        {
            maxFrameNumber = 200;
            isIdleRequired = true;
        }
    }
	else
	{
		cout << "Invalid mode of operation!" << endl;
		exit(-1);
	}

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB); // double-buffering and RGB color
    // mode.
    glutInitWindowSize(glWindowWidth, glWindowHeight);
    glutInitWindowPosition(30, 30);  // Graphics window position
    glutCreateWindow("Circles");       // Window title is "Circles"

    glutDisplayFunc(display);          // Register our display() function as the display

    if(isIdleRequired)
    {
        glutIdleFunc(idle);            // Register our idle() function
    }

    myinit();                // initialize variables

    glutMainLoop();          // Enter the event loop

    return 0;
}

/*---------------------------------------------------------------------
display(): This function is called once for _every_ frame.
---------------------------------------------------------------------*/
void display(void)
{

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glColor3f(1.0, 0.84, 0);    /* draw in golden yellow */
    glPointSize(1.0);			/* size of each point */

    for (list<CIRCLE*>::iterator it=circles.begin(); it != circles.end(); ++it)
    {
        CIRCLE *c = (CIRCLE*)(*it);

        cout << c->centerX << " " << c->centerY << " " << c->radius << " " << c->scalingFactor << endl;

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

		// scaling the circle to fit in the window
        glScalef(c->scalingFactor, c->scalingFactor, c->scalingFactor);

		// translating the world-coordinate to screen-coordinate
        glTranslatef(glWindowCenterX + abs(c->centerX) , glWindowCenterY + abs(c->centerY) , 0.0f);

        glPushMatrix();

        glBegin(GL_POINTS);

        draw_circle(c->centerX, c->centerY, (c->radius * frameNumber) / maxFrameNumber); /* draw a circle here */

        glPopMatrix();
        glEnd();
        glFlush();  /* render graphics */

    }



    glutSwapBuffers();  /* swap buffers */
    cout << " ------------------------------- " << endl;

}


/*---------------------------------------------------------------------
myinit(): Set up attributes and viewing
---------------------------------------------------------------------*/
void myinit()
{
    glClearColor(0.0, 0.0, 0.92, 0.0);    /* blue background*/

    /* set up viewing */
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0, glWindowWidth, 0.0, glWindowHeight);
    glMatrixMode(GL_MODELVIEW);
}

void idle(void)
{
    // if frame number os more than maximum allowed frame number,
    // then reset
    if (frameNumber < maxFrameNumber )
    {
        frameNumber++;
    }
    else
    {
        frameNumber = 0;
    }
    glutPostRedisplay(); // or call display()
}

void draw_circle(int x, int y, int r)
{

    int originY = 0;
    int originX = r;

    int cDelta = 1 - r;
    int eastDelta = 3;
    int westDelta = -2 * r + 5;

    // first point
    circlePoint(originX, originY);

    while(originX > originY)
    {
        if(cDelta < 0)
        {
            // choose east x
            cDelta += eastDelta;
            eastDelta += 2;
            westDelta += 2;
        }
        else
        {
            // choose the west x
            cDelta += westDelta;
            eastDelta += 2;
            westDelta += 4;
            originX--;
        }

		// increament Y-axis value
        originY++;

        // draw the new 8 points
        circlePoint(originX, originY);

    }

}

/**
* Draws 8 symmetric point on the screen
*/
void circlePoint(int x, int y)
{
    glVertex2i (x, y);
    glVertex2i (x, -y);
    glVertex2i (-x, y);
    glVertex2i (-x, -y);

    glVertex2i (y, x);
    glVertex2i (y, -x);
    glVertex2i (-y, x);
    glVertex2i (-y, -x);
}

void prepareInput()
{
    string filePath;
    cout << "Please enter a file path" << endl;
    getline(cin, filePath);

    std::ifstream fileStream(filePath.c_str());

    string nextLine;
    getline(fileStream, nextLine);
    stringstream myStream(nextLine);

    myStream >> numberOfCircles;

    cout << "numberOfCircles :: " << numberOfCircles << endl;

    while(getline(fileStream, nextLine))
    {
        string item;
        int x, y, r, counter = 0;
        int whitePos = 0, startPos = 0;

        while((whitePos = nextLine.find((' '), startPos)) != string::npos)
        {

            int val = atoi(nextLine.substr(startPos, whitePos - startPos).c_str());
            startPos = whitePos + 1;
            if (counter == 0)
            {
                x = val;
            }
            else if (counter == 1)
            {
                y = val;
            }
            else if (counter == 2)
            {
                r = val;
            }
            else
            {
                break;
            }

            counter++;
        }

        r = atoi(nextLine.substr(startPos).c_str());

        counter = 0;
        CIRCLE *c = new CIRCLE;
        c->centerX = x;
        c->centerY = y;
        c->radius = r;
        c->scalingFactor = computeScalingFactor(x, y, r);
        circles.push_back(c);

        x = y = r = 0;

    }
}

float computeScalingFactor(int x, int y, int r) {

    x = abs(x);
    y = abs(y);

    int xTranslation = x + glWindowCenterX;
    int yTranslation = y + glWindowCenterY;

    int xDec = max(xTranslation + r, abs(xTranslation - r));
    int yDec = max(yTranslation + r, abs(yTranslation - r));

    int cDec = max(xDec, yDec);

    float scaleFactor = 1.0f;
    if(cDec > glWindowWidth) {
        scaleFactor = (float)((float)glWindowWidth/(float)cDec);
    }

    return scaleFactor;
}
