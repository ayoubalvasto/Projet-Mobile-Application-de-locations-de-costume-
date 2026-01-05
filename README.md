#  Application de Location de Costumes

Application mobile Android native pour la gestion et la location de costumes, accompagnée d'un backend API Laravel.

##  Description du Projet

Cette application permet aux utilisateurs de consulter un catalogue de costumes disponibles à la location et d'effectuer des réservations. Elle comprend deux interfaces distinctes :

- **Interface Client** : Consultation du catalogue, réservation de costumes, gestion de l'historique des locations
- **Interface Admin** : Gestion complète du catalogue (CRUD), gestion des clients, suivi des réservations

## Architecture

Le projet est composé de deux parties principales :

###  Application Android (Frontend)
- **Langage** : Java
- **SDK Android** : Native Android
- **Min SDK** : 24 (Android 7.0)
- **Target SDK** : 36

### API Backend Laravel
- **Framework** : Laravel (PHP)
- **Base de données** : MySQL
- **Architecture** : API REST

##  Technologies Utilisées

### Frontend Android
- **Retrofit2** : Communication avec l'API REST
- **Gson** : Sérialisation/désérialisation JSON
- **Glide** : Chargement et cache d'images
- **RecyclerView** : Affichage performant des listes
- **CardView** : Composants UI modernes
- **Material Design** : Interface utilisateur moderne

### Backend Laravel
- **Laravel Framework** : Framework PHP moderne
- **Laravel Sanctum** : Authentification API
- **MySQL** : Base de données relationnelle
- **Eloquent ORM** : Gestion des modèles de données

## 📂 Structure du Projet

```
LocationCostumesJava/
├── app/                          # Application Android
│   ├── src/main/
│   │   ├── java/com/example/myapplication/
│   │   │   ├── MainActivity.java
│   │   │   ├── HomeActivity.java
│   │   │   ├── DetailActivity.java
│   │   │   ├── HistoryActivity.java
│   │   │   ├── ClientLoginActivity.java
│   │   │   ├── ClientRegisterActivity.java
│   │   │   ├── AdminLoginActivity.java
│   │   │   ├── AdminDashboardActivity.java
│   │   │   └── ...
│   │   ├── res/                  # Ressources (layouts, images, strings)
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── BackendLocation/              # API Laravel
│   ├── app/
│   │   ├── Http/Controllers/
│   │   │   ├── Api/
│   │   │   │   └── CostumeController.php
│   │   │   ├── LocationController.php
│   │   │   └── Admin/
│   │   ├── Models/
│   │   │   ├── Costume.php
│   │   │   ├── Location.php
│   │   │   └── User.php
│   │   └── ...
│   ├── database/
│   │   └── migrations/
│   ├── routes/
│   │   ├── api.php
│   │   └── web.php
│   └── ...
│
├── screenshots/                  # Captures d'écran du projet
├── README.md                     # Ce fichier
├── ANALYSE_PROJET.md            # Analyse détaillée du projet
└── AMELIORATIONS_APPORTEES.md   # Liste des améliorations apportées
```

## ✨ Fonctionnalités

### Pour les Clients
- ✅ Consultation du catalogue de costumes avec images
- ✅ Détails complets de chaque costume (nom, prix, taille, image)
- ✅ Réservation de costumes avec saisie des informations client
- ✅ Historique des locations effectuées
- ✅ Authentification (connexion/inscription)
- ✅ Gestion de session utilisateur

### Pour les Administrateurs
- ✅ Authentification sécurisée
- ✅ Dashboard de gestion
- ✅ Gestion complète du catalogue (CRUD)
  - Ajouter un costume
  - Modifier un costume
  - Supprimer un costume
  - Gérer les images
- ✅ Gestion des clients
  - Liste des utilisateurs inscrits
  - Informations détaillées
- ✅ Gestion des réservations
  - Liste de toutes les réservations
  - Modification du statut des réservations
  - Suivi des locations

##  Installation et Configuration

### Prérequis
- **Android Studio** (version récente)
- **Java JDK 11** ou supérieur
- **PHP 8.1** ou supérieur
- **Composer** (gestionnaire de dépendances PHP)
- **MySQL** (base de données)
- **Serveur web** (Apache/Nginx) ou PHP built-in server

### Configuration Backend (Laravel)

1. **Aller dans le dossier BackendLocation**
   ```bash
   cd BackendLocation
   ```

2. **Installer les dépendances**
   ```bash
   composer install
   ```

3. **Configurer le fichier `.env`**
   ```env
   DB_CONNECTION=mysql
   DB_HOST=127.0.0.1
   DB_PORT=3306
   DB_DATABASE=location_costumes
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ```

4. **Générer la clé d'application**
   ```bash
   php artisan key:generate
   ```

5. **Exécuter les migrations**
   ```bash
   php artisan migrate
   ```

6. **Créer un utilisateur admin** (optionnel)
   ```bash
   php artisan db:seed --class=AdminUserSeeder
   ```

7. **Démarrer le serveur**
   ```bash
   php artisan serve
   ```
   Le serveur sera accessible sur `http://localhost:8000`

### Configuration Android

1. **Ouvrir le projet dans Android Studio**
   - Ouvrir Android Studio
   - File > Open > Sélectionner le dossier `app`

2. **Configurer l'URL de l'API**
   - Ouvrir `app/src/main/java/com/example/myapplication/ApiConfig.java`
   - Modifier `BASE_URL` selon votre configuration :
     - Pour l'émulateur : `http://10.0.2.2:8000/`
     - Pour un appareil physique : `http://VOTRE_IP_LOCALE:8000/`

3. **Synchroniser Gradle**
   - Android Studio devrait synchroniser automatiquement
   - Sinon : File > Sync Project with Gradle Files

4. **Compiler et exécuter**
   - Connecter un appareil Android ou démarrer un émulateur
   - Run > Run 'app'

##  Configuration API

L'application Android communique avec le backend via une API REST. Les endpoints principaux sont :

### Endpoints Publics
- `GET /api/costumes` : Liste tous les costumes
- `POST /api/locations` : Créer une nouvelle réservation

### Endpoints Authentifiés
- `POST /api/login` : Connexion client
- `POST /api/register` : Inscription client
- `GET /api/locations/history` : Historique des locations

### Endpoints Admin
- `POST /api/admin/login` : Connexion admin
- `GET /api/admin/costumes` : Liste des costumes (admin)
- `POST /api/admin/costumes` : Créer un costume
- `PUT /api/admin/costumes/{id}` : Modifier un costume
- `DELETE /api/admin/costumes/{id}` : Supprimer un costume
- `GET /api/admin/users` : Liste des utilisateurs
- `GET /api/admin/reservations` : Liste des réservations

## 📸 Captures d'Écran

Les captures d'écran de l'application sont disponibles dans le dossier `screenshots/`.

## 📚 Documentation

- **ANALYSE_PROJET.md** : Analyse technique détaillée du projet
- **AMELIORATIONS_APPORTEES.md** : Liste complète des améliorations apportées

##  Sécurité

- Authentification via Laravel Sanctum (API)
- Validation des données côté serveur
- Gestion sécurisée des sessions
- Protection CSRF pour les routes web

##  Base de Données

### Tables Principales

**costumes**
- `id` : Identifiant unique
- `nom` : Nom du costume
- `taille` : Taille disponible
- `prix` : Prix de location
- `image` : URL de l'image
- `created_at`, `updated_at` : Timestamps

**locations**
- `id` : Identifiant unique
- `user_id` : ID de l'utilisateur
- `costume_id` : ID du costume
- `nom_client` : Nom du client
- `telephone` : Téléphone du client
- `date_debut` : Date de début de location
- `date_fin` : Date de fin de location
- `statut` : Statut de la réservation
- `note` : Note optionnelle
- `created_at`, `updated_at` : Timestamps

**users**
- Table standard Laravel pour l'authentification
- `id`, `name`, `email`, `password`, `created_at`, `updated_at`

##  Auteurs

- Ayoub Rebai

