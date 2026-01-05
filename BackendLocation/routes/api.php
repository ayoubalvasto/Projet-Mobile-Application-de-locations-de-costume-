<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\CostumeController;
use App\Http\Controllers\LocationController;
use App\Http\Controllers\AuthController;
use Illuminate\Http\Request;
use App\Http\Controllers\Api\Admin\CostumeAdminApiController;
use App\Http\Controllers\Api\Admin\UserAdminApiController;
use App\Http\Controllers\Api\Admin\LocationAdminApiController;

// Auth (publiques)
Route::post('/register', [AuthController::class, 'register']);
Route::post('/login', [AuthController::class, 'login']);

// Routes publiques (costumes et locations publiques)
Route::get('/costumes', [CostumeController::class, 'index']);
Route::get('/costumes/{id}', [CostumeController::class, 'show']);
Route::get('/locations', [LocationController::class, 'index']);

// Routes protégées (requièrent authentification Sanctum)
Route::middleware('auth:sanctum')->group(function () {
    Route::post('/logout', [AuthController::class, 'logout']);
    Route::get('/me', [AuthController::class, 'me']);

    // Locations liées à l'utilisateur connecté
    Route::post('/locations', [LocationController::class, 'store']);
    Route::get('/locations/mine', [LocationController::class, 'mine']);

    Route::get('/user', function (Request $request) {
        return $request->user();
    });
});

// Admin API (Sanctum + rôle admin)
Route::middleware(['auth:sanctum', 'admin.role'])->prefix('admin')->group(function () {
    // Costumes CRUD
    Route::get('/costumes', [CostumeAdminApiController::class, 'index']);
    Route::post('/costumes', [CostumeAdminApiController::class, 'store']);
    Route::put('/costumes/{costume}', [CostumeAdminApiController::class, 'update']);
    Route::delete('/costumes/{costume}', [CostumeAdminApiController::class, 'destroy']);

    // Clients
    Route::get('/clients', [UserAdminApiController::class, 'index']);
    Route::delete('/clients/{user}', [UserAdminApiController::class, 'destroy']);

    // Réservations
    Route::get('/reservations', [LocationAdminApiController::class, 'index']);
    Route::patch('/reservations/{location}/statut', [LocationAdminApiController::class, 'updateStatus']);
});
