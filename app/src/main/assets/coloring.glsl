// Coloring variables
vec2 z;
float its;
float mIts;

// Misc math functions
vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float interpolate(float v0, float v1, float t) {
    return (1.0 - t) * v0 + t * v1;
}

// Coloring functions
vec4 basicColoring() {
    float quotient = (its / mIts);
    float fraction = mod(quotient, 1.0) * (mIts / 100.0);
    return vec4(fraction, fraction, fraction, 1.0);
}

vec4 interpolateColoring() {
    if (its < mIts) {
        float hsq = z.x*z.x + z.y*z.y;
        float log_zn = log(hsq) / 2.0;
        float nu = log(log_zn / log(2.0)) / log(2.0);
        its = its + 1.0 - nu;
    }

    if(its == mIts) {
        return vec4(0.0, 0.0, 0.0, 1.0);
    }

    float color1 = floor(its) / mIts;
    float color2 = (floor(its) + 1.0) / mIts;
    return vec4(hsv2rgb(vec3(interpolate(color1, color2, mod(its, 1.0)), 1.0, 1.0)), 1.0);
}

// Main method
vec4 colorFractal(vec2 c, float iterations, float maxIterations, int coloringType) {
    z = c;
    its = iterations;
    mIts = maxIterations;

    if(coloringType == 0) {
        return basicColoring();
    }
    else  if(coloringType == 1) {
        return interpolateColoring();
    }
}