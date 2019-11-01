<?php
header("Content-type:application/json; utf-8");

include("api.class.php");
$api = new api();

$data = json_decode(file_get_contents('php://input'), true);

if( isset($data) )
{
    if(isset($data['action']))
    {
        switch ($data['action']) {
            case "1":
                if( isset($data['login']) && isset($data['password']) )
                    echo $api->login($data['login'], $data['password']);
                else
                    echo $api->error("Données de connexion non fournis");
                break;

            case "2":
                if( isset($data['login']) && isset($data['password'])&& isset($data['confirmPassword']) )
                    echo $api->create($data['login'], $data['password'],$data['confirmPassword']);
                else
                    echo $api->error("Données de creation non fournis");
                break;

            case "3":
                if( isset($data['login']) && isset($data['titre']) && isset($data['sondageData']) )
                    echo $api->createSondage($data['login'], $data['titre'], $data['sondageData']);
                else
                    echo $api->error("Données de creation de sondage non fournis");
                break;
            
            case "4":
                if( isset($data['admin']) )
                    echo $api->getSondage($data['admin']);
                else
                    echo $api->error("aucun nom d'asmin envoyé");
                break;
            case "5":
                if( isset($data['login']) && isset($data['titre']) && isset($data['sondageData']) )
                    echo $api->voter($data['login'], $data['titre'], $data['sondageData']);
                else
                    echo $api->error("Informations pour voter non completes");
                break;
            case "10":
                if( isset($data['admin']) && isset($data['titre']))
                    echo $api->getSondageFile($data['admin'], $data['titre']);
                else
                    echo $api->error("Informations pour récupéré non completes");
                break;

            default:
                echo $api->error("Cette action n'existe pas");
                break;
        }
    }
    else
    {
        echo $api->error("Aucune action choisie.");
    }

}
else
{
    echo $api->error('Aucune données envoyées');
}



?>
