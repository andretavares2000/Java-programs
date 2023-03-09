2D Game Agario

Este projeto foi feito de modo a criar bases em threads e perceber como é feita a comunicação entre um servidor/cliente socket.


Essencialmente, o jogo é uma versão simples do agar.io. Inicialmente são colocados 90 jogadores automáticos aleatoriamente, com vidas entre 1-3, sendo cada jogador 1 thread. O jogo só é começado ao fim de 10 segundos.

Como o espaço do jogo é limitado, e são muitos jogadores a serem inseridos aleatoriamente, algum jogador irá ser inserido em cima do outro. Para resolver isso,
o jogador que chega mais tarde só é inserido quando o jogador que ocupa inicialmente o espaço se mova. 
Para isso foi utilizado um wait/notify.

A movimentação dos jogadores é a seguinte: 
-Os jogadores que iniciam com 1 vida movem-se a cada 400ms.
-Os jogadores que iniciam com 2 vidas movem-se a cada 800ms.
-Os jogadores que iniciam com 3 vidas movem-se a cada 1200ms.
Esta movimentação é feita completamente aleatóriamente.

Considera-se um jogador vivo, um jogador que tenha entre 1-9 vidas.
Em caso de impacto entre jogadores irá ser feita um confronto entre ambos bloqueando as posições que ocupam utilizando locks, de modo a impedir confrontos entre 3 ou 
+ jogadores ao retirar a possibilidade de um jogador ir para a posição onde se encontra o caso anteriormento explicito.

Este confronto é feito da seguinte maneira:
-Caso jogador 1 tenha mais vida que o jogador 2, o jogador 2 morre e o jogador 1 fica com a sua vida somada com a vida do adversário.
-Caso jogador 1 tenha a mesma vida que o jogador 2, é feita de forma aleatória a vitória entre o confronto.

Quando um jogador morre (0 vida) torna-se num obstáculo, tal como um jogador que ganhou (10vida = X).
Quando um jogador ativo tenta ir para uma posição onde se encontra um obstáculo, irá esperar 2 segundos para poder mover-se novamente, por outras palavras fica bloqueado.
Para isso utilizei uma thread auxiliar como foi pedido no enunciado do projeto de modo a testar os meus conhecimentos apesar de haver formas mais simples de o fazer.

Quando houver 3 jogadores vencedores o jogo termina.
Para isso foi feito um CountDownLatch.

Para testar esta parte do projeto recomendo correr a partir do GameGuiMain.

2 parte do projeto é a do Servidor/Cliente.

O servidor criado pode receber vários clientes.
O servidor manda o estado do jogo a cada cliente (jogadores,suas posiçoes, etc...) a partir de uma classe Serializable de modo a conseguir enviar uma mensagem com a 
lista dos jogadores(seu id, sua vida,posiçao, e se é humano).

O cliente ao entrar no jogo irá ter um aspeto diferente para se conseguir destinguir dos jogadores automáticos. Este entra no jogo com 5 de vida e utiliza AWSD para se 
movimentar.

Apesar de estar a funcionar, esta parte do projeto causou-me alguns problemas e não tive tempo para os arranjar. Irá haver muito lag entre o envio do servidor para o
cliente.

Este projeto foi efetuado individualmente. 
