const alarmModel = require("../models/alarmModel");

exports.index = (req, res) => {
  res.render("index");
};

exports.updateDB = async (req, res) => {
  const { statut, z1, z2, z3, z4 } = req.body;
  try {
    await alarmModel.updateStatus(statut, z1, z2, z3, z4);
    console.log("Mise à jour de la base de données réussie ");
    res.status(201).send("Mise à jour de la base de données réussie :");
  } catch (err) {
    console.error("Database update error: ", err);
    res.status(500).send("Database update error");
  }
};

exports.updateZone1 = async (req, res) => {
  const { z1 } = req.body;
  try {
    await alarmModel.updateZone1(z1);
    console.log("Zone 1 mise à jour avec succès ");
    res.send("Zone 1 mise à jour avec succès ");
  } catch (err) {
    res.status(500).send("Server error");
  }
};

exports.updateZone2 = async (req, res) => {
  const { z2 } = req.body;
  try {
    const result = await alarmModel.updateZone2(z2);
    console.log("Zone 2 mise à jour avec succès");

    res.send("Zone 2 mise à jour avec succès");
  } catch (err) {
    res.status(500).send("Server error");
  }
};

exports.updateZone3 = async (req, res) => {
  const { z3 } = req.body;
  try {
    await alarmModel.updateZone3(z3);
    console.log("Zone 3 mise à jour avec succès");
    res.send("Zone 3 mise à jour avec succès");
  } catch (err) {
    res.status(500).send("Server error");
  }
};

exports.updateZone4 = async (req, res) => {
  const { z4 } = req.body;
  try {
    await alarmModel.updateZone4(z4);
    console.log("Zone 4 mise à jour avec succès");
    res.send("Zone 4 mise à jour avec succès");
  } catch (err) {
    res.status(500).send("Server error");
  }
};

exports.arm = async (req, res) => {
    const { statut } = req.body;
    
    if (statut === undefined) {
      return res.status(400).send("Mauvaise requête : le paramètre 'statut' est manquant");
    }
  
    try {
        await alarmModel.arm(statut);
        console.log("Système d'alarme armé avec succès");
        res.json({
          message: "Système d'alarme armé avec succès",
          statut: statut,
          z1: false,
          z2: false,
          z3: false,
          z4: false
      });
      }
     catch (err) {
      console.error("Erreur interne du serveur : ", err);
      res.status(500).send("Erreur interne du serveur");
    
  }
};

exports.deactivate = async (req, res) => {
  const { statut } = req.body;
  if (statut === undefined) {
    return res.status(400).send("Mauvaise requête : le paramètre 'statut' est manquant");
  }
  try {
    const result = await alarmModel.deactivate(statut);
    console.log("Système d'alarme désactivé avec succès" , result);
    res.status(200).send("Système d'alarme désactivé avec succès");
  } catch (err) {
    console.error("Erreur interne du serveur : ", err);
    res.status(500).send("Erreur interne du serveur");
  }
}

exports.reset = async (req, res) => {
  try {
    const result = await alarmModel.reset();
    console.log("Zones réinitialisées avec succès" ,result);
    res.send("Zones réinitialisées avec succès");
  } catch (err) {
    res.status(500).send("Server error");
  }
};

exports.select = async (req, res) => {
  try {
    const result = await alarmModel.select();
    console.log("L'etat du systeme actuelle : ", result);
    res.json(result);
  } catch (err) {
    res.status(500).send("Server error");
  }
};
