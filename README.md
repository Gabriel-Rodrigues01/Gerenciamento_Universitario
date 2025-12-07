🎓 Sistema de Gerenciamento Universitário: A Saga de Gabriel Rodrigues! 🚀
Olá, Gabriel Rodrigues! Prepare-se para embarcar na aventura mais épica da sua vida acadêmica (ou pelo menos da sua máquina)! Este não é apenas mais um sistema; é a sua ferramenta mágica para dominar o caos universitário com um sorriso no rosto e um café na mão.

✨ O Que é Essa Maravilha?
Este projeto é um Sistema de Gerenciamento Universitário completo, feito com carinho (e muito código Java!) para você organizar cursos, professores, disciplinas e turmas como um verdadeiro mestre Jedi da administração.

Esqueça as planilhas e os post-its perdidos! Aqui, tudo é CRUD (Criar, Ler, Atualizar, Deletar) na ponta dos seus dedos.

🌌 Funcionalidades de Outro Mundo!
Com este sistema, você poderá:

Gerenciar Cursos: Cadastre novos cursos, defina suas cargas horárias e veja a magia acontecer.

Gerenciar Disciplinas: Crie disciplinas incríveis, associe-as a cursos e prepare-se para o conhecimento.

Gerenciar Professores: Mantenha um registro dos seus heróis acadêmicos, com seus e-mails e formações estelares.

Gerenciar Turmas: Organize as turmas por semestre, defina horários e junte disciplinas com os professores certos.

Tudo Visual e Intuitivo: Uma interface JavaFX que fará seus olhos brilharem (ou pelo menos não chorarem).

🛠️ As Ferramentas dos Deuses (Tecnologias)
Construído com o que há de melhor no universo Java:
Tecnologia,Função Principal
JavaFX,Interface gráfica (GUI).
JPA / Hibernate,Mapeamento Objeto-Relacional (ORM) para acesso ao DB.
PostgreSQL,Sistema de Gerenciamento de Banco de Dados.
Maven,Gerenciamento de dependências e automação de build.
Lombok,"Geração automática de getters, setters, etc. (Menos código, mais café! ☕)"
🏃‍♂️💨 Como Fazer Essa Máquina Rodar?
Antes de tudo, Gabriel, certifique-se de ter os seguintes superpoderes instalados:

JDK 11+ (recomendamos o 21, mas o 11 já te coloca no jogo!)

Apache Maven

PostgreSQL (e que ele esteja rodando, claro!)

1. Preparando o Terreno (Configuração do Banco de Dados) 🌳
Crie o Banco de Dados: No seu PostgreSQL, crie um banco de dados chamado universidade_db.
CREATE DATABASE universidade_db;

Usuário e Senha: Certifique-se de que o usuário postgres com a senha Admin tenha acesso a este banco. Se for diferente, você precisará ajustar o arquivo src/main/resources/META-INF/persistence.xml.
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/universidade_db" />
<property name="jakarta.persistence.jdbc.user" value="postgres" />
<property name="jakarta.persistence.jdbc.password" value="Admin" />
<property name="hibernate.hbm2ddl.auto" value="create-drop" />

⚠️ Lembrete Importante: A propriedade hibernate.hbm2ddl.auto está como create-drop. Isso significa que, a cada vez que a aplicação iniciar, o Hibernate vai apagar e recriar todas as tabelas. Perfeito para testar, mas cuidado para não perder dados importantes!

2. Testando a Conexão com o Além (do Banco de Dados) 👽
Antes de mergulhar na interface gráfica, vamos ver se o banco está respondendo:

Localize: Encontre o arquivo src/main/java/view/TesteBanco.java.

Execute: Clique com o botão direito nele na sua IDE e selecione "Run 'TesteBanco.main()'".

Observe: Se tudo estiver certo, você verá mensagens no console confirmando a criação e listagem de um curso e um professor. Se der erro, o console será seu melhor amigo para depurar!

3. A Grande Estreia (Rodando a Aplicação JavaFX) 🎬
Agora, para a parte que você estava esperando! Para iniciar a interface gráfica:

Abra o Painel Maven: Na sua IDE (ex: IntelliJ IDEA), abra a aba "Maven" (geralmente à direita).

Expanda: Encontre seu projeto (GestaoUniversitaria), expanda "Plugins" e depois "javafx".

Execute: Dê um duplo clique em javafx:run.

E voilà! A janela principal do sistema deve aparecer, pronta para você gerenciar sua universidade dos sonhos!
😈 Problemas no Paraíso? (Troubleshooting)
Problema Comum,Solução Recomendada
"""JavaFX runtime components are missing""",Use mvn javafx:run! Sua IDE pode tentar rodar de um jeito que não encontra o JavaFX. O Maven sabe o caminho das pedras.
Erros de Banco de Dados,Verifique seu persistence.xml e as credenciais do PostgreSQL. O TesteBanco.java é seu melhor amigo aqui!
"""Cannot resolve symbol...""","Tente ""Invalidate Caches / Restart..."" na sua IDE e depois ""Rebuild Project"". Às vezes, a IDE fica com amnésia."

Divirta-se gerenciando, Gabriel!
E lembre-se: com grandes poderes (de gerenciamento), vêm grandes responsabilidades (de não apagar o banco de dados em produção)! 😉
