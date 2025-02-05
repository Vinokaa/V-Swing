# V-Swing
Um aplicativo de interface visual para fazer desenhos para interfaces visuais.

<br>

# Inserindo Uma Imagem de Referência

Selecione a opção *Selecionar Imagem* no canto superior esquerdo e selecione a imagem que deseja posicionar ao fundo.

![V-swing-1](https://github.com/user-attachments/assets/835d9a6f-ff09-4d9a-b1dc-eef49ed31abc)

<br>

# Desenhando

![V-swing-4](https://github.com/user-attachments/assets/9be5595c-6dcd-4359-a22a-ec1e27fb82ba)

Atualmente, o V-Swing possui 4 opções na barra esquerda, sendo elas 1 ferramenta de modificação das formas já desenhadas e 3 formas geométricas para que o usuário possa desenhar, a descrição de uso dessas opções é, de cima para baixo:

1. Ferramenta de modificação de formas.
   - Após selecionar esta opção, as bordas de cada forma desenhada será mostrada e o usuário poderá selecionar, utilizando o _Clique Esquerdo_, essa forma e realizar operações com ela, para selecionar uma forma entre formas sobrepostas clique no centro da forma desejada;
   - Com uma forma selecionada, o usuário pode deletá-la pressionando _Del_;
   - Com uma forma selecionada, o usuário pode movê-la pressionando _Clique Esquerdo_ e movimentando o mouse na direção desejada;
   - Pressionar _Esc_ com essa opção selecionada irá retornar ao modo padrão, sumindo com as bordas das formas da tela;
<br>

2. Polígono, é uma forma que possibilita a conexão de vários pontos através de linhas.
   - Pressionar _Clique Esquerdo_ enquanto _Polígono_ está selecionado irá definir o local em que o mouse está como um ponto do _Polígono_;
   - Pressionar _Enter_ enquanto _Polígono_ está selecionado finaliza o desenho do polígono, conectando o último ponto definido com o primeiro;
   - Pressionar _Esc_ enquanto _Polígono_ está selecionado cancela o desenho do polígono, removendo todos os pontos definidos e não-finalizados;
<br>

3. Elipse, é uma forma que possibilita desenhar formas circulares.
   - Com a _Elipse_ selecionada, o usuário deve pressionar _Clique Esquerdo_ nas 4 extremidades em que pretende posicionar a _Elipse_, independentemente da ordem;
<br>

4. Linha, é similar ao Polígono, porém não une o último ponto ao primeiro no fim.
   - Pressionar _Clique Esquerdo_ enquanto _Linha_ está selecionada irá definir o local em que o mouse está como um ponto da _Linha_;
   - Após o primeiro _Clique Esquerdo_, ao pressionar _Clique Esquerdo_ novamente a linha será continuada do ponto anterior;
   - Pressionar _Esc_ enquanto _Linha_ está selecionado cancela a sequência, ou seja, o próximo _Clique Esquerdo_ não irá continuar do ponto anterior;
<br>

# Exportando seu Desenho

Após finalizar seu desenho, o usuário pode selecionar a opção _Exportar_ ao topo esquerdo.

![V-swing-3](https://github.com/user-attachments/assets/ddb05466-ce59-419f-870b-54d56802ec56)

Ao selecionar a opção _Exportar_, o usuário é questionado sobre o tipo do arquivo de saída, a seguir está uma descrição de cada tipo de arquivo:

1. Arquivo de Texto (.txt)
   - Costuma ser menor em tamanho quando comparado ao Objeto, porém é mais lento para carregar quando importado em outro projeto;
   - Segue o padrão "Nome da forma{Dados necessário para reconstruir a forma}" em cada linha;
<br>

2. Objeto (.ser)
   - Utiliza a interface Serializable padrão do Java para exportar o objeto e seus atributos no momento da exportação;
   - Costuma ser maior em tamanho quando comparado ao Texto, porém é mais rápido para carregar quando importado em outro projeto;
<br>

# Importando seu Desenho para Outro Projeto

Para importar o desenho feito no V-Swing, o usuário deve utilizar a classe DesenhoImporter presente no package Importer.

![V-swing-5](https://github.com/user-attachments/assets/5a036ade-0e9c-4a97-8b28-da89b38dc961)

A classe DesenhoImporter recebe 5 parâmetros em seu método construtor:
  - A coordenada X na qual o topo superior esquerdo do desenho estará em seu novo projeto;
  - A coordenada Y na qual o topo superior esquerdo do desenho estará em seu novo projeto;
  - A largura do desenho em seu novo projeto;
  - A altura do desenho em seu novo projeto;
  - Um objeto do tipo File indicando o caminho do arquivo .txt ou .ser do desenho exportado anteriormente;
<br>

A classe DesenhoImporter é uma subclasse de JComponent, portanto, após criá-la, o usuário pode utilizá-la como um JComponent comum da biblioteca Swing do Java em seu projeto. A classe DesenhoImporter fará todas as conversões necessárias e fará com que o desenho feito no V-Swing seja adicionado ao seu projeto.
<br>

# Finalização

Esse trabalho foi realizado individualmente por mim como trabalho final da matéria Programação Orientada a Objetos.
<br><br>

Foi requisitado um trabalho que utilizasse a biblioteca Swing para criar uma aplicação com interface visual e decidi criar o V-Swing (Visual Swing) como uma aplicação de interface visual que facilitasse a criação de desenhos na biblioteca Swing e, assim, minimizar a codificação e torná-la mais como na realidade, realmente desenhando formas.
<br><br>

Acredito que esse foi um dos maiores projetos que fiz até o momento e pretendo prosseguir atualizando-o com correções de bugs e implementações de ferramentas que acabaram ficando de lado, pois não eram o foco do trabalho.
