<?php
//header("Content-type:application/json");

include("api.php");
$api = new api();

$data = json_decode(file_get_contents('php://input'), true);
print_r($data);
//n'affiche pas les erreurs
//error_reporting(0);
//ini_set('display_errors', 0);


// /!\ utiliser @$_POST au lieu de $_POST pour acceder au key car ca permet de donner null plutot qu'une erreur si la key existe pas, enfin je crois (ne pas le faire pour les if isset(...))/!\


/*===== FORMAT DES REQUETE ====*/
/*

actuellement:
{
    "data": {
        "action": 1,
        "identifiant": "toto",
        "mdp": "toto"
    }
}


ou alors:

{
    "action": 1,
    "identifiant": "toto",
    "mdp": "toto"
}


ou alors:

{
    "action": 1,
    "data": {
        "identifiant": "toto",
        "mdp": "toto"
    }
}


*/

/* ====== CODE DES ACTIONS ====== */
/*
    1-  login
    2-  ...
*/

/*
if( !isset($_POST['data']) )
{
    echo $api->error('Donnée non envoyé ou mal formé. (format correct actuellement en POST : {"data": {"action": 666, "bidule1": "truc"}} )');
    */
    //test les fonctions ici en attendant que le client java soit fait:
    //echo $api->login(@$_POST['data']['login'], @$_POST['data']['mdp']);
    /* 
    //chainage d'action pour tester hasMakeAResponse
    $api->login(@$_POST['data']['login'], @$_POST['data']['mdp']);
    $api->login(@$_POST['data']['login'], @$_POST['data']['mdp']);
    *//*
}
else
{
    if(!isset($_POST['data']['action']))
    {
        echo $api->error("Aucune action choisie.");
    }
    else
    {
        switch ($_POST['data']['action']) {
            case "1":
                //login logique
                echo $api->login(@$_POST['data']['login'], @$_POST['data']['mdp']);
                break;
            case "2":
                //logique de l'action 2
                echo $api->error("action pas implémenté");
                break;
            //et cetera...
            default:
                echo $api->error("Cette action n'existe pas");
                break;
        }
    }
}


*/
?>
