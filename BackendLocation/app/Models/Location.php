<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

class Location extends Model
{
    /**
     * Les attributs qui peuvent être assignés en masse.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'nom_client',
        'telephone',
        'nom_costume',
        'costume_id',
        'user_id',
        'date_debut',
        'date_fin',
        'statut',
        'taille',
        'note',
        'prix_total',
    ];

    /**
     * Relation avec le modèle Costume
     */
    public function costume(): BelongsTo
    {
        return $this->belongsTo(Costume::class);
    }

    /**
     * Relation avec le modèle User (si nécessaire dans le futur)
     */
    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }
}
