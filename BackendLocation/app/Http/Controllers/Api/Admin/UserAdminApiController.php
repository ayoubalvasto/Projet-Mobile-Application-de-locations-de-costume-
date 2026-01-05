<?php

namespace App\Http\Controllers\Api\Admin;

use App\Http\Controllers\Controller;
use App\Models\User;

class UserAdminApiController extends Controller
{
    public function index()
    {
        $clients = User::orderBy('id', 'desc')->get();
        return response()->json(['success' => true, 'data' => $clients, 'count' => $clients->count()]);
    }

    public function destroy(User $user)
    {
        // Ne pas permettre de supprimer soi-même ou l'admin principal si besoin
        if ($user->role === 'admin') {
            return response()->json(['success' => false, 'message' => 'Impossible de supprimer un admin'], 403);
        }
        $user->delete();
        return response()->json(['success' => true, 'message' => 'Client supprimé']);
    }
}


