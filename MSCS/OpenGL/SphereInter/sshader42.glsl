

attribute vec3 vPosition;

varying vec4 color;

uniform mat4 model_view;
uniform mat4 projection;
uniform mat4 at;
uniform vec4 sLight;

void main()
{
    vec4 vPosition4 = vec4(vPosition.x, vPosition.y, vPosition.z, 1.0);
    vec4 transformedPoint =  model_view * vPosition4;

    float divisor = (sLight.y - transformedPoint.y);
    float xComp = sLight.x/divisor;
    float yComp = sLight.y/divisor;
    float zComp = sLight.z/divisor;

    mat4 shadowMatrix = mat4(
        vec4(yComp, 0.0, 0.0, 0.0),
        vec4(-xComp, 0.0, -zComp, 0.0),
        vec4(0.0, 0.0, yComp, 0.0),
        vec4(0.0, 0.0, 0.0, 1.0)
    );
    
    transformedPoint = shadowMatrix * transformedPoint; 
    gl_Position = projection * at * transformedPoint;
    color = vec4(0.25, 0.25, 0.25, 0.65);
}
