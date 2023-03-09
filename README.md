# Java-programs
My favorite java programms

2D Fireman Game

Este projeto ajudou me a criar bases sobre herenças e classes abstratas em java tal como muitas outras funcionalidades do java.

Trata-se de um jogo em que se lê um ficheiro txt para criar o mapa (árvores,bombeiro,fogos nas suas posições iniciais,bulldozer). Após a leitura do ficheiro, os fogos 
tem uma probabilidade de espalhar de acordo com o tipo de arvore que está ao seu redor.O fogo não se espalha para zonas sem vegetação.Os bulldozers removem as arvores
de modo a evitar que os fogos espalhem. Bombeiro ao ir para cima de um fogo irá apaga-lo. A vegetação tem x vida de acordo com a sua caracteristica (pinheiro, relva, etc...)

Cada movimento do bombeiro conta como -1 vida ao objeto que está a arder. Caso a vida chegue a 0, a vegetação morre tornando-se em cinzas, perdendo 1 ponto. Ao remover
árvores com bulldozer também irá remover pontos ao jogador dai ser necessário utilizar quando já existe muitos fogos ativos.

Existe também uma opção de chamar um avião a partir da tecla P. Este começa a na posiçao X onde terá mais fogos verticalmente e anda 2 em 2 por movimento do jogador 
apagando os fogos pelo caminho 
Para entrar no bulldozer basta ir para cima dele e para sair basta clicar na tecla ENTER.

O jogador começa com x pontos dependendo da quantidade de vegetação for inserida no ficheiro txt lido inicialmente, ou seja, a pontuação minima equivale a 0.
No final do jogo o scor final do jogador irá ser inserido num ficheiro a parte caso seja melhor que um dos top5 scores.

Este projeto foi efetuado indivudualmente.
Existe possibilidade do jogo crashar por isso é necessario reiniciar caso isso aconteça.
