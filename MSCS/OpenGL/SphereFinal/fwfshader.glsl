
varying vec4 wPosition;
varying vec4 color;

void main()
{
    if(wPosition.y < 0.5) {
        discard;
    }
            
    gl_FragColor = color;
}