/*****************************
 * File: fshader42.glsl
 *       A simple fragment shader
 *****************************/

// #version 150 

varying vec4 color;
varying vec2 texCoord;

uniform int fogType;
uniform int enableFloorTexture;
uniform int sphereTexture;
uniform int objectType;
uniform sampler2D texture;
uniform sampler1D stexture;

void main() 
{ 
    vec4 finColor = color;   
    if (objectType == 1) {
        if(enableFloorTexture == 1) {
            finColor = color * texture2D( texture, texCoord );
        }
    } else if (objectType == 2) {
        if(sphereTexture != 0) {
            finColor = color * texture1D( stexture, texCoord.x );
        }
    }
    
    // hard coding the fog stuff    
    float fogFactor = 0.0;
    float fogDepth = (18.0 - 0.0);
    float fogDensity = 0.09;
    vec4 fogColor = vec4(0.7, 0.7, 0.7, 0.5);
    float z = gl_FragCoord.z / gl_FragCoord.w;
    
    if(fogType == 1) {
        // linear
        fogFactor = (18.0 - z) / fogDepth;
    } else if (fogType == 2) {
        // exponential
        fogFactor = exp( -fogDensity * z  );
    } else if (fogType == 3) {
        // square exponential
        fogFactor = exp( -fogDensity * fogDensity * 
		z * z );
    }
    fogFactor = clamp(fogFactor, 0.0, 1.0);

    if(fogType == 0) {
        gl_FragColor = finColor; 
    } else {
        gl_FragColor = mix(fogColor, finColor, fogFactor);
    }    
} 

