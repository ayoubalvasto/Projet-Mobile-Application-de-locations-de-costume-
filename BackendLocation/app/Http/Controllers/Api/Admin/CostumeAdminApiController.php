<?php

namespace App\Http\Controllers\Api\Admin;

use App\Http\Controllers\Controller;
use App\Models\Costume;
use Illuminate\Http\Request;

class CostumeAdminApiController extends Controller
{
    public function index()
    {
        $costumes = Costume::orderBy('id', 'desc')->get();
        return response()->json(['success' => true, 'data' => $costumes, 'count' => $costumes->count()]);
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'nom' => 'required|string|max:255',
            'taille' => 'required|string|max:50',
            'prix' => 'required|numeric|min:0',
            'image' => 'nullable|string|max:2048',
        ]);

        $costume = Costume::create($validated);
        return response()->json(['success' => true, 'message' => 'Costume créé', 'data' => $costume], 201);
    }

    public function update(Request $request, Costume $costume)
    {
        $validated = $request->validate([
            'nom' => 'required|string|max:255',
            'taille' => 'required|string|max:50',
            'prix' => 'required|numeric|min:0',
            'image' => 'nullable|string|max:2048',
        ]);

        $costume->update($validated);
        return response()->json(['success' => true, 'message' => 'Costume mis à jour', 'data' => $costume]);
    }

    public function destroy(Costume $costume)
    {
        $costume->delete();
        return response()->json(['success' => true, 'message' => 'Costume supprimé']);
    }
}


