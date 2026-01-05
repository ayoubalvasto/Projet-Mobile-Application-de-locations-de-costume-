<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;

class Costume extends Model
{
    /**
     * Les attributs qui peuvent être assignés en masse.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'nom',
        'taille',
        'prix',
        'image',
    ];

    /**
     * Relation avec les locations
     */
    public function locations(): HasMany
    {
        return $this->hasMany(Location::class);
    }
}
