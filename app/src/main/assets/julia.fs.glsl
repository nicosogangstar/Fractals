precision highp float;
uniform vec2 viewportDimensions;
uniform vec2 juliaPos;
uniform float maxIterations;

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

vec4 basicColoring(vec2 z, float its) {
    float quotient = (its / maxIterations);
    float fraction = mod(quotient, 1.0) * (maxIterations / 100.0);
    return vec4(fraction, fraction, fraction, 1.0);
}

// Main function
void main() {
    vec2 c = vec2(
        gl_FragCoord.x / viewportDimensions.x,
        gl_FragCoord.y / viewportDimensions.y
    );

    gl_FragColor = basicColoring(c, standardJulia(c));
}
