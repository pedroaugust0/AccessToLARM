
# Acesso Ao LARM
É um aplicativo para abrir a porta do Laboratório de Automação é Robótica Móvel (LARM), desenvolvido no Android Studio, com Android 4.4 (*KitKat*) como versão mínima requerida.

Utiliza conexão via *Socket* com o servidor, o qual realiza uma consulta no Banco de Dados e permite ou não a entrada de acordo com a **Credencial** inserida no aplicativo.
 
## *Activity*
O aplicativo possui uma *Activity* divida em duas Aba:
   - **Abrir**:
        Com apenas o botão de ``ABRIR``, que serve para criar a conexão e mandar a Credencial para o servidor.
        Imagem da Aba 1:
        ![Open](https://uploaddeimagens.com.br/imagens/a2l-open-jpg)

  
   - **Configurações**:
        Com três campos,  ``IP``, ``HOST``, ``CREDENCIAL``, onde o priemiro e o segundo são para inserir os dados do servidor, e o terceiro a credencial que será enviada, e com o botõa ``SALVAR`` o qual salvará os dados, de forma que só é necessário inseri-los uma vez.
         Imagem da Aba 2:
        ![Settings](https://uploaddeimagens.com.br/imagens/a2l-settings-jpg)

## Tradução
O aplicativo possui tradução integral para o Português BR e para *English* US

## Limitações
Existem algumas limitações no projeto, as quais são:
   - O usuário não é informado quando há um problema de comunicação via *Socket* com o servidor.
   - Não há comunicação entre o servidor e o aplicativo, a comunicação é apenas na direção Aplicativo -> Servidor.
   - Não há um pop-up (*Toast*) dizendo que a porta foi aberta, só o encerramento do aviso (*loading*) na tela do aplicativo.


## LICENÇA 
Este projeto foi desenvolvido sob a licença Apache 2, o countéudo integral da licença pode ser encontrado [AQUI](LINCENSE).

## CONTATO
Para entrar em contato com o desenvolvedor, pode-se utilziar o seguinte e-mail: <pedroaugust0@outlook.com>