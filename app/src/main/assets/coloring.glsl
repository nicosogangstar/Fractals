// Coloring functions
vec4 basicColoring(vec2 z, float its, float mIts) {
    float quotient = (its / mIts);
    float fraction = mod(quotient, 1.0) * (mIts / 100.0);
    return vec4(fraction, fraction, fraction, 1.0);
}