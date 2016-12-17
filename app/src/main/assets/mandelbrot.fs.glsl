precision highp float;

// All fractals
uniform vec2 viewportDimensions;
uniform float maxIterations;

// Mandelbrot specific
uniform vec4 bounds;

// Mandelbrot functions
float standardMandelbrot(vec2 c) {
    vec2 z = c;
    float its = 0.0;
    for(int i = 0; i < int(maxIterations); i++) {
        float t = 2.0 * z.x * z.y + c.y;
        z.x = z.x * z.x - z.y * z.y + c.x;
        z.y = t;

        if(z.x * z.x + z.y * z.y > 4.0) {
            break;
        }

        its += 1.0;
    }

    return its;
}

float mandelbrotN(vec2 c) {
    vec2 z = c;
    float its = 0.0;
    for(int i = 0; i < int(maxIterations); i++) {
        float xtmp = 2.0 * z.x * z.y + c.y;
        z.x = z.x * z.x - z.y * z.y + c.x;
        z.y = xtmp;

        if(z.x * z.x + z.y * z.y > 4.0) {
            break;
        }
        its += 1.0;
    }
    return its;
}

// Main function
void main() {
    vec2 c = vec2(
        gl_FragCoord.x * (bounds.w - bounds.z) / viewportDimensions.x + bounds.z,
        gl_FragCoord.y * (bounds.y - bounds.x) / viewportDimensions.y + bounds.x
    );

    gl_FragColor = basicColoring(c, standardMandelbrot(c), maxIterations);
}
