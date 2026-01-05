<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Costume;
use Illuminate\Http\Request;

class CostumeAdminController extends Controller
{
    public function index()
    {
        $costumes = Costume::orderBy('id', 'desc')->paginate(20);
        return view('admin.costumes.index', compact('costumes'));
    }

    public function create()
    {
        return view('admin.costumes.create');
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'nom' => 'required|string|max:255',
            'taille' => 'required|string|max:50',
            'prix' => 'required|numeric|min:0',
            'image' => 'nullable|string|max:2048',
        ]);

        Costume::create($validated);

        return redirect()->route('admin.costumes.index')->with('success', 'Costume créé.');
    }

    public function edit(Costume $costume)
    {
        return view('admin.costumes.edit', compact('costume'));
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

        return redirect()->route('admin.costumes.index')->with('success', 'Costume mis à jour.');
    }

    public function destroy(Costume $costume)
    {
        $costume->delete();
        return redirect()->route('admin.costumes.index')->with('success', 'Costume supprimé.');
    }
}


