#version 400 core

in vec2 vertex;
in vec2 textureCoords;

out vec2 passTextureCoords;

uniform vec2 position;
uniform float scale;

void main(void){
	float universalScaler = 0.4;
	gl_Position = vec4((vertex + position) * scale * universalScaler, 0.0, 1.0);
	passTextureCoords = textureCoords;
}