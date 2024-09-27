// External imports
const express = require("express");

// Internal imports
const alarmController = require("../Controllers/alarmController");

// variable
const router = express.Router();

// Route update les donnees de l'alarme
router
  .route('/updatedb')
  .post(alarmController.updateDB);

// Routes update zones 1,2,3,4
router
  .route('/updateZone1')
  .post(alarmController.updateZone1);

router
  .route('/updateZone2')
  .post(alarmController.updateZone2);

router
  .route('/updateZone3')
  .post(alarmController.updateZone3);

router
  .route('/updateZone4')
  .post(alarmController.updateZone4);

// Route pour armer l'alarme
router
  .route('/arm')
  .post(alarmController.arm);

// Route pour desactiver l'alarme
router
  .route('/deactivate')
  .post(alarmController.deactivate);

// Route update les zones
router
  .route('/reset')
  .post(alarmController.reset);

// Route pour select
router
  .route('/select')
  .post(alarmController.select);

module.exports = router;