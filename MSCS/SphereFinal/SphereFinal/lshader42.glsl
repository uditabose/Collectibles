/***************************
 * File: lshader42.glsl:
 *   A lighting shading vertex shader.
 *
 ***************************/

// #version 150  // YJC: Comment/un-comment this line to resolve compilation errors
                 //      due to different settings of the default GLSL version

attribute  vec3 vPosition;
attribute  vec3 vNormal;
attribute  vec3 vColor;
attribute  vec2 vTexCoord;

varying vec2 texCoord;
varying vec4 color;

// lighting and shading

uniform vec4 DistantAmbientProduct, 
             DistantDiffuseProduct, 
             DistantSpecularProduct, 
             NearAmbientProduct, 
             NearDiffuseProduct, 
             NearSpecularProduct,
             GlobalAmbientProduct;

uniform mat3 Normal_Matrix;

uniform int DistanceLight;
uniform vec4 DistantLightPosition;   // Must be in Eye Frame
uniform float Shininess;

/* for spot and distant lights */
uniform int SpotLight;
uniform vec4 NearLightPosition;
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
    // viewer position
    vec3 E = normalize( -pos ); 
    // Transform vertex normal into eye coordinates
    vec3 N = normalize(Normal_Matrix * vNormal);

    vec3 L; // direction of light
    float attenuation;
    
    /*--------- calculation pertaining to distant light only --------------*/ 
    L = vec3(DistantLightPosition.x, DistantLightPosition.y, DistantLightPosition.z);
    vec3 H = normalize( L + E );
    attenuation = 1.0;
    
    // ambient after distant light
    vec4 ambient = DistantAmbientProduct;
    
    // diffuse after distant light
    float d = max( dot(L, N), 0.0 );
    vec4  diffuse = attenuation * d *  DistantDiffuseProduct;

    // specular after distant light
    float s = pow( max(dot(N, H), 0.0), Shininess );
    vec4  specular = s * DistantSpecularProduct;
    if( dot(L, N) < 0.0 ) {
        specular = vec4(0.0, 0.0, 0.0, 1.0);
    }
    specular = attenuation * specular;

    // color after distant light
    color = ambient + diffuse + specular + GlobalAmbientProduct;
 
    if(DistanceLight == 0) {
        // point or spot light, along with distant source
        L = normalize( NearLightPosition.xyz - pos );
        float distance = length(NearLightPosition.xyz - pos);
        attenuation = 1.0 / (ConstAtt + LinearAtt * distance + QuadAtt * distance * distance);

        if(SpotLight == 1) {
            
            float clampedCosine = max(0.0, dot(-L, (SpotlightPosition.xyz - pos)));
            if (clampedCosine < cos(radians(SpotlightCutOffAngle))) // outside of spotlight cone?
            {
              attenuation = 0.0;
            }
            else
            {
              attenuation = attenuation * pow(clampedCosine, SpotlightExp);   
            }
        }
        
        // recalculate halfway vector for near light
        H = normalize( L + E );

        // ambient after near light
        ambient = attenuation * NearAmbientProduct;

        // diffuse after near light
        d = max( dot(L, N), 0.0 );
        diffuse = attenuation * d *  NearDiffuseProduct;

        // specular after near light
        s = pow( max(dot(N, H), 0.0), Shininess );
        vec4  specular = s * NearSpecularProduct;
        if( dot(L, N) < 0.0 ) {
            specular = vec4(0.0, 0.0, 0.0, 1.0);
        }
        specular = attenuation * specular;

        // color after near light
        color = color + ambient + diffuse + specular;
        // color = ambient + diffuse + specular;
    }
    texCoord = vTexCoord;
    gl_Position = projection * model_view * vPosition4;

}