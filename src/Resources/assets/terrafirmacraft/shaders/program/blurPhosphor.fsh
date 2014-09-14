#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;
uniform float Radius;

uniform vec3 Phosphor = vec3(0.7, 0.7, 0.7);
uniform float LerpFactor = 1.0;

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    for(float r = -Radius; r <= Radius; r += 1.0) {
        float strength = abs(1.0 - r / Radius);
        strength = strength * strength;
        totalStrength = totalStrength + strength;
        blurred = blurred + texture2D(DiffuseSampler, texCoord + oneTexel * r * BlurDir) * strength;
    }
    
    vec4 CurrTexel = texture2D(DiffuseSampler, texCoord);
    vec4 PrevTexel = texture2D(PrevSampler, texCoord);
    
    gl_FragColor = vec4(max(PrevTexel.rgb * Phosphor, blurred.rgb / totalStrength), texture2D(DiffuseSampler, texCoord).a);
}
