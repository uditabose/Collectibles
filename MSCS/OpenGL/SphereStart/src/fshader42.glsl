/*****************************
 * File: fshader42.glsl
 *       A simple fragment shader
 *****************************/

// #version 150  // YJC: Comment/un-comment this line to resolve compilation errors
                 //      due to different settings of the default GLSL version
//attribute vec4 color;
 varying vec4 color;
 //vec4 fColor;

void main() 
{ 
    //vec4 vColor4 = vec4( 1.0, 0.0, 1.0, 1.0); 
    gl_FragColor = color; 
} 

