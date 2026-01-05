<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up()
    {
        Schema::create('costumes', function (Blueprint $table) {
            $table->id();
            $table->string('nom');
            $table->string('taille');
            $table->decimal('prix', 8, 2);
            $table->string('image')->nullable(); // URL de l'image
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('costumes');
    }
};
