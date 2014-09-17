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
attribute  vec3 vNormal;
varying vec4 color;

// lighting and shading

uniform vec4 AmbientProduct, DiffuseProduct, SpecularProduct, GlobalAmbientProduct;
uniform mat3 Normal_Matrix;
uniform vec4 LightPosition;   // Must be in Eye Frame
uniform float Shininess;
uniform int DistanceLight;
/* for spot and distant lights */
uniform vec4 SpotlightPosition; // the point spot light looks at
uniform float SpotlightCutOffAngle;
uniform float SpotlightExp;


uniform float ConstAtt;  // Constant Attenuation
uniform float LinearAtt; // Linear Attenuation
uniform float QuadAtt;   // Quadratic Attenuation

uniform mat4 model_view;
uniform mat4 projection;

void main()
{
    vec4 vPosition4 = vec4(vPosition.x, vPosition.y, vPosition.z, 1.0);
    vec4 vNormal4 = vec4(vNormal.x, vNormal.y, vNormal.z, 0.0);

    // Transform vertex  position into eye coordinates
    vec3 pos = (model_view * vPosition4).xyz;

    vec3 L; // direction of light
    float attenuation;
    if(DistanceLight == 1) {
        // distant, directional light
        L = vec3(LightPosition.x, LightPosition.y, LightPosition.z);
        attenuation = 1.0;
    } else {
        // point or spot light
        L = normalize( LightPosition.xyz - pos );
        float distance = length(LightPosition.xyz - pos);
        attenuation = 1.0 / (ConstAtt + LinearAtt * distance + QuadAtt * distance * distance);

        if(SpotlightCutOffAngle < 90.0) {
            float clampedCosine = max(0.0, dot(-L, SpotlightPosition.xyz));
            if (clampedCosine < cos(radians(SpotlightCutOffAngle))) // outside of spotlight cone?
            {
              attenuation = 0.0;
            }
            else
            {
              attenuation = attenuation * pow(clampedCosine, SpotlightExp);   
            }
        }
    }

    vec3 E = normalize( -pos );
    vec3 H = normalize( L + E );

    // Transform vertex normal into eye coordinates
    vec3 N = normalize(Normal_Matrix * vNormal);

    vec4 ambient = AmbientProduct;

    float d = max( dot(L, N), 0.0 );
    vec4  diffuse = attenuation * d *  DiffuseProduct;

    float s = pow( max(dot(N, H), 0.0), Shininess );
    vec4  specular = s * SpecularProduct;

    if( dot(L, N) < 0.0 ) {
        specular = vec4(0.0, 0.0, 0.0, 1.0);
    }

    specular = attenuation * specular;

    color = ambient + diffuse + specular + GlobalAmbientProduct;

    // YJC: Original, incorrect below:
    //      gl_Position = projection*model_view*vPosition/vPosition.w;


    gl_Position = projection * model_view * vPosition4;

}