## ing-sw-2021-Staiano-Tissot-Romeo
Progetto Ing. Sw.
![Masters-of-Renaissance](http://www.craniocreations.it/wp-content/uploads/2019/06/Masters-of-Renaissance_box3D_ombra.png)

Welcome to the ing-sw-2021-Staiano-Tissot-Romeo Masters of Renaissance JAVA edition!<br />
Prova Finale di Ingegneria Del Software<br />
Anno Accademico 2020-2021<br />
Requisiti<br />

1 Introduzione<br />
2 Requisiti di Progetto<br />
2.1 Requisiti Game-specific<br />
2.2 Requisiti Game-agnostic<br />
2.2.1 Server<br />
2.2.2 Client<br />
2.3 Funzionalità Avanzate<br />
3 Valutazione<br />

1 Introduzione<br />
Il progetto consiste nello sviluppo di una versione software del gioco da tavolo Maestri del Rinascimento.<br />
Il progetto finale dovrà includere:<br />
• diagramma UML iniziale dell’applicazione (ad alto livello);<br />
• diagrammi UML finali che mostrino come è stato progettato il software, i diagrammi potranno essere generati a partire dal codice sorgente del progetto utilizzando tool automatici;<br />
• implementazione funzionante del gioco conforme alle regole del gioco e alle specifiche presenti in questo documento;<br />
• codice sorgente dell’implementazione;<br />
• codice sorgente dei test di unità.<br />
Data di consegna: venerdì 02 luglio 2021 23:59:59 CEST<br />
Data di valutazione: da fissare (a partire da lunedì 5 Luglio 2021)<br />
Modalità di valutazione, aule e orari, verranno comunicati successivamente.<br />

2 Requisiti di Progetto<br />
I requisiti del progetto si dividono in due gruppi:<br />
I Requisiti game-specific che riguardano le regole e le meccaniche del gioco.<br />
II Requisiti game-agnostic che riguardano aspetti di design, tecnologici o implementativi.<br />

2.1 Requisiti Game-specific<br />
Le regole del gioco sono descritte nel file maestri-rules.pdf, caricato su BeeP. In caso di discordanza tra tale file ed eventuali traduzioni in altre lingue, si faccia riferimento alla versione italiana. I nomi di classi, interfacce, le variabili, ed in generale tutti gli identificativi nel codice dovranno essere in lingua inglese. Sempre in inglese dovranno essere anche i commenti nel codice e la documentazione tecnica (JavaDoc).<br />
Per la valutazione (vedi Tabella 1) si fa riferimento a due possibili set di regole: le regole semplificate e le regole complete.<br />
• Regole Semplificate: si considerino solo le regole per lo svolgimento di partite da 2 a 4 giocatori; è esclusa la partita in solitario.<br />
• Regole Complete: si considerino le regole per le partite da 1 a 4 giocatori, quindi inclusa la partita in solitario.<br />
Ogni giocatore è identificato da un nickname che viene impostato lato client e deve essere univoco in ogni partita. L’univocità del nickname deve essere garantita dal server in fase di accettazione del giocatore.<br />

2.2 Requisiti Game-agnostic<br />
In questa sezione vengono presentati i requisiti tecnici dell’applicazione.<br />
Il progetto consiste nell’implementazione di un sistema distribuito composto da un singolo server in grado di gestire una partita alla volta e multipli client (uno per giocatore) che possono partecipare ad una sola partita alla volta. Si richiede l’utilizzo del pattern MVC (Model-View-Controller) per progettare l’intero sistema.<br />

2.2.1 Server<br />
Di seguito la lista dei requisiti tecnici per il lato server.<br />
• Deve implementare le regole del gioco utilizzando JavaSE.<br />
• Deve essere istanziato una sola volta al fine di gestire una singola partita (tranne nel caso in cui venga implementata la funzionalità avanzata “partite multiple”).<br />

2.2.2 Client<br />
Di seguito la lista dei requisiti tecnici per il lato client.<br />
• Deve essere implementato con JavaSE ed essere instanziabile più volte (una per giocatore).<br />
• L’interfaccia grafica deve essere implementata mediante Swing o JavaFX.<br />
• Nel caso in cui venga implementata sia un’interfaccia testuale (CLI) che un’interfaccia grafica (GUI), all’avvio, deve permettere al giocatore di selezionare il tipo di interfaccia da utilizzare.<br />
Si assume che ogni giocatore che voglia partecipare ad una partita conosca l’indirizzo IP o lo URL del server.<br />
Quando un giocatore si connette:<br />
• Se non ci sono partite in fase di avvio, viene creata una nuova partita, altrimenti l’utente entra automaticamente a far parte della partita in fase di avvio.<br />
• Il giocatore che crea la partita sceglie il numero di giocatori che ne fanno parte.<br />
• Se c’è una partita in fase di avvio, il giocatore viene automaticamente aggiunto alla partita.<br />
• La partita inizia non appena si raggiunge il numero di giocatori atteso (in base alla scelta effettuata dal primo giocatore in fase di creazione della partita).<br />
Il server consente ai vari giocatori di svolgere i propri turni secondo le regole del gioco. È necessario gestire sia il caso in cui i giocatori escano dalla partita, sia il caso in cui cada la connessione di rete. In entrambi i casi la partita dovrà terminare e tutti i giocatori verranno notificati.<br />

2.3 Funzionalità Avanzate<br />
Le funzionalità avanzate sono requisiti facoltativi da implementare al fine di incrementare il punteggio in fase di valutazione. Queste funzionalità vengono valutate solo se i requisiti game-specific e game-agonistic presentati nelle sezioni precedenti sono stati implementati in maniera sufficiente. Le funzionalità avanzate implementabili sono:<br />
• Partite multiple: Realizzare il server in modo che possa gestire più partite contemporaneamente, dopo la procedura di creazione della prima partita, i giocatori che accederanno al server verranno gestiti in una sala d’attesa per creare una seconda partita e così via.<br />
• Persistenza: Lo stato di una partita deve essere salvato su disco, in modo che la partita possa riprendere anche a seguito dell’interruzione dell’esecuzione del server. Per riprendere una partita, i giocatori si devono ricollegare al server utilizzando gli stessi nickname una volta che questo sia tornato attivo. Si assume che il server non interrompa la propria esecuzione durante il salvataggio su disco e che il disco costituisca una memoria totalmente affidabile.<br />
• Editor dei parametri: Realizzare un’applicazione GUI aggiuntiva che permetta di modificare almeno i seguenti parametri di gioco: costo, punti vittoria e potere di produzione delle carte sviluppo; numero, requisiti, punti vittoria e abilità speciali delle carte leader (le abilità speciali stesse devono essere modificabili), poteri di produzione di base delle plance personali, sezioni Rapporto in Vaticano e punti vittoria nel Tracciato Fede. La configurazione del gioco deve essere memorizzata in un file. Tale file dovrà essere utilizzabile per avviare una partita con i parametri modificati.<br />
• Partita locale: Realizzare il client in modo che possa gestire la partita in solitario senza effettuare una connessione con il server.<br />
• Resilienza alle disconnessioni: I giocatori disconnessi possono ricollegarsi in seguito e continuare la partita. Mentre un giocatore non è collegato, il gioco continua saltando i turni di quel giocatore.<br />

3 Valutazione<br />
In Tabella 1 sono riportati i punteggi massimi ottenibili in base ai requisiti implementati.<br />
Requisiti Soddisfatti Voto Massimo<br />
Regole Semplificate + CLI + Socket 18<br />
Regole Complete + CLI + Socket 21<br />
Regole Complete + CLI + GUI + Socket 24<br />
Regole Complete + CLI + GUI + Socket + 1 FA 27<br />
Regole Complete + CLI + GUI + Socket + 2 FA 30<br />
Regole Complete + CLI + GUI + Socket + 3 FA 30L<br />

Di seguito si riportano gli aspetti che vengono valutati e contribuiscono alla definizione del punteggio finale:<br />
• La qualità della progettazione, con particolare riferimento ad un uso appropriato di interfacce, ereditarietà, composizione tra classi, uso dei design pattern (statici, di comunicazione e architetturali) e divisione delle responsabilità.<br />
• La stabilità dell’implementazione e la conformità alle specifiche.<br />
• La leggibilità del codice scritto, con particolare riferimento a nomi di variabili/metodi/classi/package, all’inserimento di commenti in inglese e documentazione JavaDoc in inglese, la mancanza di codice ripetuto e metodi di eccessiva lunghezza.<br />
• L’efficacia e la copertura dei casi di test, il nome e i commenti di ogni test dovranno chiaramente specificare le funzionalità testate e i componenti coinvolti.<br />
• L’utilizzo degli strumenti (IntelliJ IDEA, Git, Maven, ...).<br />
• L’autonomia, l’impegno e la comunicazione (con i responsabili e all’interno del gruppo) durante tutte le fasi del progetto.<br /><br />
<img width="711" alt="model_coverage" src="https://user-images.githubusercontent.com/25418541/119738638-4bff1900-be81-11eb-9cdc-0a28892f93b6.png"><br />
<img width="711" alt="all_model_coverage" src="https://user-images.githubusercontent.com/25418541/119738652-515c6380-be81-11eb-8d4e-5b4ae20b3917.png"><br />
<img width="711" alt="controller_coverage" src="https://user-images.githubusercontent.com/25418541/119738674-591c0800-be81-11eb-82dc-88eba3b9eda3.png"><br />

![Cranio-Creations](https://www.migliorigiochi.eu/wp-content/webp-express/webp-images/doc-root/wp-content/uploads/2018/07/cranio-creation-750x403.jpg.webp)
