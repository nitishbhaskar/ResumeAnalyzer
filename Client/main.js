var resumeParser = angular.module("ResumeParser", []);

resumeParser.service("resumeService", function () {
    var that = this;
    this.skills = "";
    this.link = "";
    this.degree = ["MS",
        "M.S",
        "Master’s",
        "M.S.",
        "Master's",
        "Master",
        "Masters",
        "BS",
        "B.S.",
        "Bachelor's",
        "Bachelor’s",
        "Bachelor",
        "Bachelors",
        "PHD",
        "Phd",
        "Doctrate"];

    this.myMap = new Map();

    this.allSkills = ["A# .NET", "A#", "A-0 System", "A+", "A++", "ABAP", "ABC", "ABC ALGOL", "ABSET", "ABSYS", "ACC", "Accent", "Ace DASL", "ACL2", "Avicsoft", "ACT-III", "Action!", "ActionScript", "Ada", "Adenine", "Agda", "Agilent VEE", "Agora", "AIMMS", "Alef", "ALF", "ALGOL 58", "ALGOL 60", "ALGOL 68", "ALGOL W", "Alice", "Alma-0", "AmbientTalk", "Amiga E", "AMOS", "AMPL", "Apex (Salesforce.com)", "APL", "App Inventor for Android's visual block language", "AppleScript", "Arc", "ARexx", "Argus", "AspectJ", "Assembly language", "ATS", "Ateji PX", "AutoHotkey", "Autocoder", "AutoIt", "AutoLISP / Visual LISP", "Averest", "AWK", "Axum", "Active Server Pages", "ASP.NET", "B", "Babbage", "Bash", "BASIC", "bc", "BCPL", "BeanShell", "Batch (Windows/Dos)", "Bertrand", "BETA", "Bistro", "BitC", "BLISS", "Blockly", "BlooP", "Boo", "Boomerang", "Bourne shell (including bash and ksh)", "BREW", "BPEL", "Business Basic", "C[edit]", "C", "C--", "C++", "C#", "C/AL", "Caché ObjectScript", "C Shell", "Caml", "Cayenne", "CDuce", "Cecil", "Cesil", "Céu", "Ceylon", "CFEngine", "CFML", "Cg", "Ch", "Chapel", "Charity", "Charm", "Chef", "CHILL", "CHIP-8", "chomski", "ChucK", "CICS", "Cilk", "Citrine (programming language)", "CL (IBM)", "Claire", "Clarion", "Clean", "Clipper", "CLIPS", "CLIST", "Clojure", "CLU", "CMS-2", "COBOL", "CobolScript", "Cobra", "CODE", "CoffeeScript", "ColdFusion", "COMAL", "Combined Programming Language (CPL)", "COMIT", "Common Intermediate Language (CIL)", "Common Lisp (also known as CL)", "COMPASS", "Component Pascal", "Constraint Handling Rules (CHR)", "COMTRAN", "Converge", "Cool", "Coq", "Coral 66", "Corn", "CorVision", "COWSEL", "CPL", "Cryptol", "csh", "Csound", "CSP", "CUDA", "Curl", "Curry", "Cybil", "Cyclone", "Cython", "D", "DASL (Datapoint's Advanced Systems Language)", "DASL (Distributed Application Specification Language)", "Dart", "DataFlex", "Datalog", "DATATRIEVE", "dBase", "dc", "DCL", "Deesel (formerly G)", "Delphi", "DinkC", "DIBOL", "Dog", "Draco", "DRAKON", "Dylan", "DYNAMO", "E", "E#", "EarSketch", "Ease", "Easy PL/I", "Easy Programming Language", "EASYTRIEVE PLUS", "ECMAScript", "Edinburgh IMP", "EGL", "Eiffel", "ELAN", "Elixir", "Elm", "Emacs Lisp", "Emerald", "Epigram", "EPL", "Erlang", "es", "Escher", "ESPOL", "Esterel", "Etoys", "Euclid", "Euler", "Euphoria", "EusLisp Robot Programming Language", "CMS EXEC (EXEC)", "EXEC 2", "Executable UML", "F[edit]", "F", "F#", "F*", "Factor", "Falcon", "Fantom", "FAUST", "FFP", "Fjölnir", "FL", "Flavors", "Flex", "FlooP", "FLOW-MATIC", "FOCAL", "FOCUS", "FOIL", "FORMAC", "@Formula", "Forth", "Fortran – ISO/IEC 1539", "Fortress", "FoxBase", "FoxPro", "FP", "Franz Lisp", "Frege", "F-Script", "G[edit]", "G", "Game Maker Language", "GameMonkey Script", "GAMS", "GAP", "G-code", "GDScript", "Genie", "GDL", "GJ", "GEORGE", "GLSL", "GNU E", "GM", "Go", "Go!", "GOAL", "Gödel", "Golo", "GOM (Good Old Mad)", "Google Apps Script", "Gosu", "GOTRAN", "GPSS", "GraphTalk", "GRASS", "Groovy", "H[edit]", "Hack", "HAGGIS", "HAL/S", "Hamilton C shell", "Harbour", "Hartmann pipelines", "Haskell", "Haxe", "Hermes", "High Level Assembly", "HLSL", "Hop", "Hopscotch", "Hope", "Hugo", "Hume", "HyperTalk", "I[edit]", "IBM Basic assembly language", "IBM HAScript", "IBM Informix-4GL", "IBM RPG", "ICI", "Icon", "Id", "IDL", "Idris", "IMP", "Inform", "INTERLISP", "Io", "Ioke", "IPL", "IPTSCRAE", "ISLISP", "ISPF", "ISWIM", "J[edit]", "J", "J#", "J++", "JADE", "JAL", "Janus (concurrent constraint programming language)", "Janus (time-reversible computing programming language)", "JASS", "Java", "JavaScript", "JCL", "JEAN", "Join Java", "JOSS", "Joule", "JOVIAL", "Joy", "JScript", "JScript .NET", "JavaFX Script", "Julia", "Jython", "K[edit]", "K", "Kaleidoscope", "Karel", "Karel++", "KEE", "Kixtart", "Klerer-May System", "KIF", "Kojo", "Kotlin", "KRC", "KRL", "KRL (KUKA Robot Language)", "KRYPTON", "ksh", "L[edit]", "L", "L# .NET", "LabVIEW", "Ladder", "Lagoona", "LANSA", "Lasso", "Lava", "LC-3", "Leda", "Legoscript", "LIL", "LilyPond", "Limbo", "Limnor", "LINC", "Lingo", "LIS", "LISA", "Lisaac", "Lisp – ISO/IEC 13816", "Lite-C", "Lithe", "Little b", "Logo", "Logtalk", "LotusScript", "LPC", "LSE", "LSL", "LiveScript", "Lua", "Lucid", "Lustre", "LYaPAS", "Lynx", "M[edit]", "M2001", "M4", "M#", "Machine code", "MAD (Michigan Algorithm Decoder)", "MAD/I", "Magik", "Magma", "make", "Maude system", "Maple", "MAPPER (now part of BIS)", "MARK-IV (now VISION:BUILDER)", "Mary", "MASM Microsoft Assembly x86", "MATH-MATIC", "Mathematica", "MATLAB", "Maxima (see also Macsyma)", "Max (Max Msp – Graphical Programming Environment)", "MaxScript internal language 3D Studio Max", "Maya (MEL)", "MDL", "Mercury", "Mesa", "Metafont", "Microcode", "MicroScript", "MIIS", "Milk (programming language)", "MIMIC", "Mirah", "Miranda", "MIVA Script", "ML", "Model 204", "Modelica", "Modula", "Modula-2", "Modula-3", "Mohol", "MOO", "Mortran", "Mouse", "MPD", "Mathcad", "MSIL – deprecated name for CIL", "MSL", "MUMPS", "Mystic Programming Language (MPL)", "N[edit]", "NASM", "Napier88", "Neko", "Nemerle", "nesC", "NESL", "Net.Data", "NetLogo", "NetRexx", "NewLISP", "NEWP", "Newspeak", "NewtonScript", "NGL", "Nial", "Nice", "Nickle", "Nim", "NO", "NPL", "Not eXactly C (NXC)", "Not Quite C (NQC)", "NSIS", "Nu", "Numpy", "NWScript", "NXT-G", "NPL", "O[edit]", "o:XML", "Oak", "Oberon", "OBJ2", "Object Lisp", "ObjectLOGO", "Object REXX", "Object Pascal", "Objective-C", "Objective-J", "Obliq", "OCaml", "occam", "occam-π", "Octave", "OmniMark", "Onyx", "Opa", "Opal", "OpenCL", "OpenEdge ABL", "OPL", "OpenVera", "OPS5", "OptimJ", "Orc", "ORCA/Modula-2", "Oriel", "Orwell", "Oxygene", "Oz", "P[edit]", "P′′", "P#", "ParaSail (programming language)", "PARI/GP", "Pascal – ISO 7185", "PCASTL", "PCF", "PEARL", "PeopleCode", "Perl", "PDL", "Perl 6", "Pharo", "PHP", "Pico", "Picolisp", "Pict", "Pike", "PIKT", "PILOT", "Pipelines", "Pizza", "PL-11", "PL/0", "PL/B", "PL/C", "PL/I – ISO 6160", "PL/M", "PL/P", "PL/SQL", "PL360", "PLANC", "Plankalkül", "Planner", "PLEX", "PLEXIL", "Plus", "POP-11", "POP-2", "PostScript", "PortablE", "Powerhouse", "PowerBuilder – 4GL GUI application generator from Sybase", "PowerShell", "PPL", "Processing", "Processing.js", "Prograph", "PROIV", "Prolog", "PROMAL", "Promela", "PROSE modeling language", "PROTEL", "ProvideX", "Pro*C", "Pure", "Pure Data", "Python", "Q (equational programming language)", "Q (programming language from Kx Systems)", "Qalb", "QtScript", "QuakeC", "QPL", "R[edit]", "R", "R++", "Racket", "RAPID", "Rapira", "Ratfiv", "Ratfor", "rc", "REBOL", "Red", "Redcode", "REFAL", "Reia", "REXX", "Rlab", "ROOP", "RPG", "RPL", "RSL", "RTL/2", "Ruby", "RuneScript", "Rust", "S[edit]", "S", "S2", "S3", "S-Lang", "S-PLUS", "SA-C", "SabreTalk", "SAIL", "SALSA", "SAM76", "SAS", "SASL", "Sather", "Sawzall", "SBL", "Scala", "Scheme", "Scilab", "Scratch", "Script.NET", "Sed", "Seed7", "Self", "SenseTalk", "SequenceL", "SETL", "SIMPOL", "SIGNAL", "SiMPLE", "SIMSCRIPT", "Simula", "Simulink", "Singularity", "SISAL", "SLIP", "SMALL", "Smalltalk", "Small Basic", "SML", "Strongtalk", "Snap!", "SNOBOL(SPITBOL)", "Snowball", "SOL", "Solidity", "SPARK", "Speedcode", "SPIN", "SP/k", "SPS", "SQL", "SQR", "Squeak", "Squirrel", "SR", "S/SL", "Stackless Python", "Starlogo", "Strand", "Stata", "Stateflow", "Subtext", "SuperCollider", "SuperTalk", "Swift (Apple programming language)", "Swift (parallel scripting language)", "SYMPL", "SyncCharts", "SystemVerilog", "T[edit]", "T", "TACL", "TACPOL", "TADS", "TAL", "Tcl", "Tea", "TECO", "TELCOMP", "TeX", "TEX", "TIE", "Timber", "TMG, compiler-compiler", "Tom", "TOM", "TouchDevelop", "Toi", "Topspeed", "TPU", "Trac", "TTM", "T-SQL", "Transcript", "TTCN", "Turing", "TUTOR", "TXL", "TypeScript", "Turbo C++", "U[edit]", "Ubercode", "UCSD Pascal", "Umple", "Unicon", "Uniface", "UNITY", "Unix shell", "UnrealScript", "V[edit]", "Vala", "Verilog", "VHDL", "Visual Basic", "Visual Basic .NET", "Visual DataFlex", "Visual DialogScript", "Visual Fortran", "Visual FoxPro", "Visual J++", "Visual J#", "Visual Objects", "Visual Prolog", "VSXu", "vvvv", "W[edit]", "WATFIV, WATFOR", "WebDNA", "WebQL", "Whiley", "Windows PowerShell", "Winbatch", "Wolfram Language", "Wyvern", "X[edit]", "XBL", "XC (exploits XMOS architecture)", "xHarbour", "XL", "Xojo", "XOTcl", "XPL", "XPL0", "XQuery", "XSB", "XSharp", "XSLT – see XPath", "Xtend", "X++", "Y[edit]", "Yorick", "YQL", "Yoix", "Z[edit]", "Z notation", "Zeno", "ZOPL", "ZPL", "Zsh", "Adobe Illustrator", "Adobe InDesign", "Adobe Photoshop", "Analytics", "Android", "APIs", "Art Design", "AutoCAD", "Backup Management", "C", "C++", "Certifications", "Client Server", "Client Support", "Configuration", "Content Managment", "Content Management Systems (CMS)", "Corel Draw", "Corel Word Perfect", "CSS", "Data Analytics", "Desktop Publishing", "Design", "Diagnostics", "Documentation", "End User Support", "Email", "Engineering", "Excel", "FileMaker Pro", "Fortran", "Graphic Design", "Hardware", "Help Desk", "HTML", "Implementation", "Installation", "Internet", "iOS", "iPhone", "Linux", "Java", "Javascript", "Mac", "Matlab", "Maya", "Microsoft Excel", "Microsoft Office", "Microsoft Outlook", "Microsoft Publisher", "Microsoft Word", "Microsoft Visual", "Mobile", "MySQL", "Networks", "Open Source Software", "Oracle", "Perl", "PHP", "Presentations", "Processing", "Programming", "PT Modeler", "Python", "QuickBooks", "Ruby", "Shade", "Software", "Spreadsheet", "SQL", "Support", "Systems Administration", "Tech Support", "Troubleshooting", "Unix", "UI/UX", "Web Page Design", "Windows", "Word Processing", "XML", "XHTML", "React", "Ember", "Angular", "REST", "AWS", "AngularJS", "Bootstrap", "WCF", "IIS", "JSON", "MVC"];


    this.allSkills.forEach(function (skill) {
        this.myMap.set(skill.toLowerCase());
    }, this);

    this.submit = function () {
        this.skills = this.skills.replace(/(\r\n|\n|\r|\/)/gm, " ");
        this.skills = this.skills.replace(/,/g, ' ');
        var arrayDescription = this.skills.split(" ");
        this.matchedSkills = "";
        this.prev = "";
        arrayDescription.forEach(function (requirement) {
            requirement = requirement.toLowerCase();
            this.matchedSkills += this.myMap.has(requirement) ? " " + requirement : "";
            this.prev = requirement;
        }, this);

        this.matchedSkills = this.matchedSkills.trim().split(" ");
        this.skillsSet = new Set();
        this.matchedSkills.forEach(function(skill){
            this.skillsSet.add(skill);
        }, this);
        this.matchedSkills = Array.from(this.skillsSet);
    };

    this.download = function () {
        this.matchedSkills = this.matchedSkills.toString().split(",");
        var encodedUri = "data:text/json;charset=utf-16," + encodeURIComponent(JSON.stringify({
                "reqSkills": this.matchedSkills,
                "degree": this.degree
            }));
        this.link = encodedUri;
        this.downloadLink = document.getElementById('downloadLink');
        this.downloadLink.download = "abc.json";
        this.downloadLink.href = encodedUri;
        this.downloadLink.download = "requirements.json";
        this.downloadLink.click();
    };
});

resumeParser.controller("mainController", ['$scope', '$rootScope', 'resumeService', '$document',
    function ($scope, $rootScope, resumeService, document) {
        $scope.Main = resumeService;
        resumeService.document = document;
    }]);
