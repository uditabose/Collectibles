/***************************
 * File: vshader42.glsl:
 *   A simple vertex shader.
 *
 * - Vertex attributes (positions & colors) for all vertices are sent
 *   to the GPU via a vertex buffer object created in the OpenGL program.
 *
 * - This vertex shader uses the Model-View and Projection matrices passed
 *   on from the OpenGL program as uniform variables of type mat4.
 ***************************/

// #version 150  // YJC: Comment/un-comment this line to resolve compilation errors
                 //      due to different settings of the default GLSL version

attribute  vec3 vPosition;
attribute  vec3 vColor;
attribute  vec2 vTexCoord;

varying vec2 texCoord;
varying vec4 color;

uniform mat4 model_view;
uniform mat4 projection;

void main()
{
    vec4 vPosition4 = vec4(vPosition.x, vPosition.y, vPosition.z, 1.0);
    vec4 vColor4 = vec4(vColor.r, vColor.g, vColor.b, 1.0);

    vPosition4 = projection * model_view * vPosition4;

    gl_Position = vPosition4;
    
    color = vColor4;
    texCoord = vTexCoord;
}
