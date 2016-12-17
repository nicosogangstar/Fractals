precision highp float;

//TODO CHANGE THIS FROM JUST BEING JULIA SET

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

// Coloring function
vec4 basicColoring(vec2 z, float its) {
    float quotient = (its / maxIterations);
    float fraction = mod(quotient, 1.0) * (maxIterations / 100.0);
    return vec4(fraction, fraction, fraction, 1.0);
}

// Main function
void main() {
//-2f, 2f, -2f, 2f
    vec2 c = vec2(
        gl_FragCoord.x * 4.0 / viewportDimensions.x - 2.0,
        gl_FragCoord.y * 4.0 / viewportDimensions.y - 2.0
    );

    gl_FragColor = basicColoring(c, standardJulia(c));
}
