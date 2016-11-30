precision highp float;
uniform vec2 viewportDimensions;
uniform vec4 bounds;
uniform float n;
uniform float maxIterations;

// Misc math functions
vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float interpolate(float v0, float v1, float t) {
    return (1.0 - t) * v0 + t * v1;
}

// Fractal functions
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
        float xtmp = pow((z.x*z.x+z.y*z.y),(n/2.0))*cos(n*atan(z.y,z.x)) + c.x;
        z.y = pow((z.x*z.x+z.y*z.y),(n/2.0))*sin(n*atan(z.y,z.x)) + c.y;
        z.x = xtmp;

        if(z.x * z.x + z.y * z.y > 4.0) {
            break;
        }
        its += 1.0;
    }
    return its;
}

// Coloring functions
vec4 linearInterpolation(vec2 z, float its) {
    if (its < maxIterations) {
        float hsq = z.x*z.x + z.y*z.y;
        float log_zn = log(hsq) / 2.0;
        float nu = log(log_zn / log(2.0)) / log(2.0);
        its = its + 1.0 - nu;
    }

    if(its == maxIterations) {
        return vec4(0.0, 0.0, 0.0, 1.0);
    }
    else {
        float color1 = floor(its) / maxIterations;
        float color2 = (floor(its) + 1.0) / maxIterations;
        return vec4(hsv2rgb(vec3(interpolate(color1, color2, mod(its, 1.0)), 1.0, 1.0)), 1.0);
    }
}

vec4 basicColoring(vec2 z, float its) {
    float quotient = (its / maxIterations);
    float fraction = mod(quotient, 1.0) * (maxIterations / 100.0);
    return vec4(fraction, fraction, fraction, 1.0);
}

// Main function
void main() {
    vec2 c = vec2(
        gl_FragCoord.x * (bounds.w - bounds.z) / viewportDimensions.x + bounds.z,
        gl_FragCoord.y * (bounds.y - bounds.x) / viewportDimensions.y + bounds.x
    );

    gl_FragColor = basicColoring(c, standardMandelbrot(c));
}
