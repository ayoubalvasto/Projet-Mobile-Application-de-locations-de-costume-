<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Location;
use Illuminate\Http\Request;
use Illuminate\Validation\Rule;

class LocationAdminController extends Controller
{
    public function index()
    {
        $reservations = Location::with(['costume', 'user'])
            ->orderBy('id', 'desc')
            ->paginate(30);

        return view('admin.reservations.index', compact('reservations'));
    }

    public function updateStatus(Request $request, Location $location)
    {
        $validated = $request->validate([
            'statut' => ['required', Rule::in(['en_attente', 'confirmée', 'annulée', 'terminée'])],
        ]);

        $location->update([
            'statut' => $validated['statut'],
        ]);

        return redirect()->route('admin.reservations.index')->with('success', 'Statut mis à jour.');
    }
}


