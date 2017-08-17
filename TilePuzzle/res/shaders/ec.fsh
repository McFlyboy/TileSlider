#version 400 core

in vec2 passTextureCoords;

out vec4 outColor;

uniform sampler2D textureImg;

void main(void){
	outColor = texture(textureImg, passTextureCoords);
}