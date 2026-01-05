<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Admin\CostumeAdminController;
use App\Http\Controllers\Admin\UserAdminController;
use App\Http\Controllers\Admin\LocationAdminController;

Route::get('/', function () {
    return view('welcome');
});

Route::middleware('admin.basic')->prefix('admin')->name('admin.')->group(function () {
    Route::get('/', fn () => redirect()->route('admin.costumes.index'))->name('home');

    // Costumes (CRUD)
    Route::get('/costumes', [CostumeAdminController::class, 'index'])->name('costumes.index');
    Route::get('/costumes/create', [CostumeAdminController::class, 'create'])->name('costumes.create');
    Route::post('/costumes', [CostumeAdminController::class, 'store'])->name('costumes.store');
    Route::get('/costumes/{costume}/edit', [CostumeAdminController::class, 'edit'])->name('costumes.edit');
    Route::put('/costumes/{costume}', [CostumeAdminController::class, 'update'])->name('costumes.update');
    Route::delete('/costumes/{costume}', [CostumeAdminController::class, 'destroy'])->name('costumes.destroy');

    // Clients
    Route::get('/clients', [UserAdminController::class, 'index'])->name('clients.index');

    // Réservations
    Route::get('/reservations', [LocationAdminController::class, 'index'])->name('reservations.index');
    Route::patch('/reservations/{location}/statut', [LocationAdminController::class, 'updateStatus'])->name('reservations.status');
});
