
# Acesso Ao LARM
É um aplicativo para abrir a porta do Laboratório de Automação é Robótica Móvel (LARM), desenvolvido no Android Studio, com Android 4.4 (*KitKat*) como versão mínima requerida.

Utiliza conexão via *Socket* com o servidor, o qual realiza uma consulta no Banco de Dados e permite ou não a entrada de acordo com a **Credencial** inserida no aplicativo.

## APK's
- API menor 19 (KitKat)
  - [2.3.1](APKs/AccessToLARM-2.3.1-API-16.apk)
- API 19 (KitKat) ou superior
  - ~~[2.3.1](APKs/AccessToLARM-2.3.1.apk)~~
  - [2.4.1](APKs/AccessToLARM-2.4.1.apk)

## *Activity*
O aplicativo possui uma *Activity* divida em duas Aba:
   - **Abrir**:
        Com apenas o botão de ``ABRIR``, que serve para criar a conexão e mandar a Credencial para o servidor.
        
        Imagem da Aba 1:
        
        ![Abrir](https://uploaddeimagens.com.br/images/000/944/963/original/A2L-open.jpg?1496938328)

  
   - **Configurações**:
        Com quatro campos,  ``IP``, ``PORT``, ``CREDENCIAL``, ``Wifi Password`` onde o priemiro e o segundo são para inserir os dados do servidor, e o terceiro a credencial que será enviada, o quarto campo é para inserir a senha do Wifi do LARM, e com o botõa ``SALVAR`` o qual salvará os dados, de forma que só é necessário inseri-los uma vez.
         
        Imagem da Aba 2:
        
        ![Configuraçes](https://uploaddeimagens.com.br/images/000/944/964/original/A2L-settings.jpg?1496938375)

## Tradução
O aplicativo possui tradução integral para o Português BR e para *English* US

## Limitações
Existem algumas limitações no projeto, as quais são:
   - O usuário não é informado quando há um problema de comunicação via *Socket* com o servidor.
   - Não há comunicação entre o servidor e o aplicativo, a comunicação é apenas na direção Aplicativo -> Servidor.
   - Não há um pop-up (*Toast*) dizendo que a porta foi aberta, só o encerramento do aviso (*loading*) na tela do aplicativo.


## LICENÇA 
Este projeto foi desenvolvido sob a licença Apache 2, o countéudo integral da licença pode ser encontrado [AQUI](./LICENSE).

## CONTATO
Para entrar em contato com o desenvolvedor, pode-se utilziar o seguinte e-mail: <pedroaugust0@live.com>
