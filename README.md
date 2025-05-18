# MushrimSpawner
Plugin para Spigot 1.8.9 que permite dar spawners funcionais de qualquer mob com um comando simples, além de autocomplete para facilitar o uso.

📜 Funcionalidades
``Comando /givesp <mob> <quantidade> <player> para dar spawners de qualquer mob spawnável.``

- Autocomplete inteligente para o nome dos mobs e jogadores.
- Spawner funcional que realmente gera o mob escolhido, não apenas spawner genérico.
- O spawner recebe nome personalizado: Gerador de <mob>.
- Permissões para controle de uso.
- Código otimizado, leve e confiável.

📥 Instalação
Coloque o arquivo .jar do plugin na pasta plugins do seu servidor Spigot 1.8.9.

Reinicie ou recarregue o servidor.

O comando /givesp estará disponível.

/givesp <mob> <quantidade> <player>
<mob> — Nome do mob (exemplo: ZOMBIE, SKELETON, CREEPER). Use TAB para listar os mobs disponíveis.

<quantidade> — Número de spawners a entregar (1 a 64).

<player> — Jogador que irá receber o spawner.

🔒 Permissões
Permissão	Descrição
``mushrim.givespawner	Permite usar o comando /givesp``

Exemplo
``/givesp zombie 3 Hariel``
Entrega 3 spawners que geram zombies para o jogador Hariel.

🛠️ Desenvolvimento
Baseado em Spigot API 1.8.9.

Utiliza NMS para modificar o EntityId do spawner no item.
Código modular com comando e tab-complete.

🤝 Contribuição
Pull requests e sugestões são bem-vindos!

📄 Licença
Use e modifique livremente, atribuindo créditos ao autor original.
