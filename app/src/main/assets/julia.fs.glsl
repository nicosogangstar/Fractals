precision highp float;

// All fractals
uniform vec2 viewportDimensions;
uniform float maxIterations;

// Julia specific
uniform vec2 juliaPos;

// Standard Julia function
float standardJulia(vec2 c) {
    vec2 z = c;
    float its = 0.0;
    for(int i = 0; i < int(maxIterations); i++) {
        c = z;
        z.x = c.x * c.x - c.y * c.y + juliaPos.x;
        z.y = 2.0 * c.x * c.y + juliaPos.y;

        if(z.x * z.x + z.y * z.y > 4.0) {
            break;
        }

        its += 1.0;
    }
    return its;
}

// Main function
void main() {
//-2f, 2f, -2f, 2f
    vec2 c = vec2(
        gl_FragCoord.x * 4.0 / viewportDimensions.x - 2.0,
        gl_FragCoord.y * 4.0 / viewportDimensions.y - 2.0
    );

    gl_FragColor = basicColoring(c, standardJulia(c), maxIterations);
}
