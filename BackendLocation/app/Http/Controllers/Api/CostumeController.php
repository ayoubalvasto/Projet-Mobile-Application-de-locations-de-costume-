<?php
namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Costume;
use Illuminate\Http\Request;

class CostumeController extends Controller
{
    /**
     * Récupérer tous les costumes
     */
    public function index()
    {
        try {
            $costumes = Costume::orderBy('nom', 'asc')->get();
            
            return response()->json([
                'success' => true,
                'data' => $costumes,
                'count' => $costumes->count()
            ], 200);

        } catch (\Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Erreur lors de la récupération des costumes',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    /**
     * Récupérer un costume par ID
     */
    public function show($id)
    {
        try {
            $costume = Costume::with('locations')->findOrFail($id);
            
            return response()->json([
                'success' => true,
                'data' => $costume
            ], 200);

        } catch (\Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Costume non trouvé',
                'error' => $e->getMessage()
            ], 404);
        }
    }
}
