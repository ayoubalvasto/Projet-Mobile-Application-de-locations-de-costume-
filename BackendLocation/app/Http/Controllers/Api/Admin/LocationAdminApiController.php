<?php

namespace App\Http\Controllers\Api\Admin;

use App\Http\Controllers\Controller;
use App\Models\Location;
use Illuminate\Http\Request;
use Illuminate\Validation\Rule;

class LocationAdminApiController extends Controller
{
    public function index()
    {
        $reservations = Location::with(['costume', 'user'])
            ->orderBy('id', 'desc')
            ->get();

        return response()->json(['success' => true, 'data' => $reservations, 'count' => $reservations->count()]);
    }

    public function updateStatus(Request $request, Location $location)
    {
        $validated = $request->validate([
            'statut' => ['required', Rule::in(['en_attente', 'confirmée', 'annulée', 'terminée'])],
        ]);

        $location->update(['statut' => $validated['statut']]);

        return response()->json(['success' => true, 'message' => 'Statut mis à jour', 'data' => $location]);
    }
}


