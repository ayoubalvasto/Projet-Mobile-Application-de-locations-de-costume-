<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::table('locations', function (Blueprint $table) {
            // Ajouter une clé étrangère vers costumes (optionnel pour compatibilité)
            $table->foreignId('costume_id')->nullable()->after('id')->constrained('costumes')->onDelete('cascade');
            
            // Ajouter des dates de location (optionnel)
            $table->date('date_debut')->nullable()->after('nom_costume');
            $table->date('date_fin')->nullable()->after('date_debut');
            
            // Ajouter un statut (optionnel)
            $table->enum('statut', ['en_attente', 'confirmée', 'annulée', 'terminée'])->default('en_attente')->after('date_fin');
            
            // Ajouter un index pour améliorer les performances
            $table->index('nom_client');
            $table->index('statut');
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::table('locations', function (Blueprint $table) {
            $table->dropForeign(['costume_id']);
            $table->dropIndex(['nom_client']);
            $table->dropIndex(['statut']);
            $table->dropColumn(['costume_id', 'date_debut', 'date_fin', 'statut']);
        });
    }
};
