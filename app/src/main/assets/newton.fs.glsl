precision highp float;

uniform vec2 viewportDimensions;
uniform float maxIterations;
uniform int coloringType;
uniform vec4 bounds;

float M_PI = 3.141592653589;
bvec2 trueVec = bvec2(true, true);

float f(float x, float d) {
    return pow(x, d)-1.0;
}

float fprime(float x, float d) {
    return d*pow(x,(d-1.0));
}

float newton(vec2 c) {
    vec2 r1 = vec2(1, 0);
    vec2 r2 = vec2(-0.5,  sin(2.0*M_PI/3.0));
    vec2 r3 = vec2(-0.5, -sin(2.0*M_PI/3.0));
    vec2 tolerance = vec2(0.0001, 0.0001);

    float d = 3.0;
    float i = 1.0;
    float z = c.x + i*c.y;
    float iterations = 0.0;

    while(greaterThanEqual(abs(z-r1), tolerance) == trueVec &&
          greaterThanEqual(abs(z-r2), tolerance) == trueVec &&
          greaterThanEqual(abs(z-r3), tolerance) == trueVec) {

        if(z != 0.0) {
            z = z - (z * z * z - 1.0) / (z * z * 3.0);
            z=z-(f(z,d)/fprime(z,d));
        }

        if(iterations == maxIterations) {
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
