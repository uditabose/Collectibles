// Vertex shader for firework

attribute vec4 vColor;
attribute vec3 vVelocity;

uniform vec4 initPosition;
uniform float timeElapsed;
uniform mat4 lookAt;

varying vec4 wPosition;
varying vec4 color;

void main()
{  
    float x = initPosition.x + 0.001 * vVelocity.x * timeElapsed;
    float y = initPosition.y + 0.001 * vVelocity.y * timeElapsed 
                - 0.5 * 0.00000049 * timeElapsed * timeElapsed;
    float z = initPosition.z + 0.001 * vVelocity.z * timeElapsed;

    wPosition = vec4(x, y, z, 1.0);
    color = vColor;
    gl_Position = lookAt * wPosition;
}