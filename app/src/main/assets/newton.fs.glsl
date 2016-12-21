precision highp float;

uniform vec2 viewportDimensions;
uniform float maxIterations;
uniform int coloringType;
uniform vec4 bounds;

float M_PI = 3.141592653589;
bvec2 trueVec = bvec2(true, true);

float newton(vec2 c) {
    vec2 r1 = vec2(1, 0);
    vec2 r2 = vec2(-0.5,  sin(2.0*M_PI/3.0));
    vec2 r3 = vec2(-0.5, -sin(2.0*M_PI/3.0));
    vec2 tolerance = vec2(0.0001, 0.0001);

    vec2 z = c;
    float iterations = 0.0;

    vec2 a1 = abs(z-r1);
    vec2 a2 = abs(z-r2);
    vec2 a3 = abs(z-r3);

    while(greaterThanEqual(a1, tolerance) == trueVec &&
          greaterThanEqual(a2, tolerance) == trueVec &&
          greaterThanEqual(a3, tolerance) == trueVec) {

        if(z.x != 0.0 && z.y != 0.0) {
            z = z - (z * z * z - 1.0) / (z * z * 3.0);
        }

        if(iterations < maxIterations) {
            break;
        }

        iterations += 1.0;
    }
    return iterations;
}

void main() {
    vec2 c = vec2(
        gl_FragCoord.x * (bounds.w - bounds.z) / viewportDimensions.x + bounds.z,
        gl_FragCoord.y * (bounds.y - bounds.x) / viewportDimensions.y + bounds.x
    );

    gl_FragColor = colorFractal(coloringType, c, newton(c), maxIterations);
}
