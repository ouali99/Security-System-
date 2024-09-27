// Récupère la connexion à la base de données
const getDb = () => {
    if (!global.db) {
        throw new Error('Connexion à la base de données non établie.');
    }
    return global.db;
};

// Met a jour le statut de l'alarme
const updateStatus = async (statut, z1, z2, z3, z4) => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET statut = ?, z1 = ?, z2 = ?, z3 = ?, z4 = ? WHERE id = 1`;
    try {
        const [results] = await db.query(sql, [statut, z1, z2, z3, z4]);
        return results;
    } catch (error) {
        throw error;
    }
};

// Mettre a jour la zone 1
const updateZone1 = async (zone1) => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET z1 = ?, z2 = 'FALSE', z3 = 'FALSE', z4 = 'FALSE' WHERE id = 1`;
    try {
      const [results] = await db.query(sql, [zone1]);
      return results;
    } catch (error) {
      throw error;
    }
  };
  
  // Mettre a jour la zone 2
  const updateZone2 = async (zone2) => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET z2 = ?, z1 = 'FALSE', z3 = 'FALSE', z4 = 'FALSE' WHERE id = 1`;
    try {
      const [results] = await db.query(sql, [zone2]);
      return results;
    } catch (error) {
      throw error;
    }
  };
  
  // Mettre a jour la zone 3
  const updateZone3 = async (zone3) => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET z3 = ?, z1 = 'FALSE', z2 = 'FALSE', z4 = 'FALSE' WHERE id = 1`;
    try {
      const [results] = await db.query(sql, [zone3]);
      return results;
    } catch (error) {
      throw error;
    }
  };
  
  // Mettre a jour la zone 4
  const updateZone4 = async (zone4) => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET z4 = ?, z1 = 'FALSE', z2 = 'FALSE', z3 = 'FALSE' WHERE id = 1`;
    try {
      const [results] = await db.query(sql, [zone4]);
      return results;
    } catch (error) {
      throw error;
    }
  };

// Arm the alarm system
const arm = async (statut) => {
  const db = getDb();
  const sql = `UPDATE AlarmStatus SET statut = ?, z1 = 'FALSE', z2 = 'FALSE', z3 = 'FALSE', z4 = 'FALSE' WHERE id = 1`;
  try {
      const [results] = await db.query(sql, [statut]);
      return results;
  } catch (error) {
      throw error;
  }
};

//Deactivation 
const deactivate = async(statut) => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET statut = 'FALSE', z1 = 'FALSE', z2 = 'FALSE', z3 = 'FALSE', z4 = 'FALSE' WHERE id = 1`;
    try {
        const [results] = await db.query(sql, [statut]);
        return results;
    } catch (error) {
        throw error;
    }
}

// Reinitialiser les zones
const reset = async () => {
    const db = getDb();
    const sql = `UPDATE AlarmStatus SET z1 = 'FALSE', z2 = 'FALSE', z3 = 'FALSE', z4 = 'FALSE' WHERE id = 1`;
    try {
        const [results] = await db.query(sql);
        return results;
    } catch (error) {
        throw error;
    }
};

// Sélectionner le statut actuel
const select = async () => {
    const db = getDb();
    const sql = `SELECT * FROM AlarmStatus WHERE id = 1`;
    try {
        const [results] = await db.query(sql);
        return results[0];
    } catch (error) {
        throw error;
    }
};

module.exports = {
    updateStatus,
    updateZone1,
    updateZone2,
    updateZone3,
    updateZone4,
    arm,
    deactivate,
    reset,
    select
};
