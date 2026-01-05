<?php

namespace App\Http\Controllers;

use App\Models\Location;
use App\Models\Costume;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\Rule;
use Carbon\Carbon;

class LocationController extends Controller
{
    /**
     * Créer une nouvelle location avec validation
     */
    public function store(Request $request)
    {
        // Nécessite un utilisateur authentifié (middleware auth:sanctum)
        $user = $request->user();
        if (!$user) {
            return response()->json([
                'success' => false,
                'message' => 'Authentification requise'
            ], 401);
        }

        // Validation des données
        $validator = Validator::make($request->all(), [
            'nom_client' => 'required|string|max:255',
            'telephone' => 'nullable|string|max:50',
            'costume_id' => 'required|integer|exists:costumes,id',
            'date_debut' => 'required|date',
            'date_fin' => 'required|date|after_or_equal:date_debut',
            'taille' => 'nullable|string|max:50',
            'note' => 'nullable|string|max:500',
            'statut' => ['nullable', Rule::in(['en_attente', 'confirmée', 'annulée', 'terminée'])],
        ]);

        if ($validator->fails()) {
            return response()->json([
                'success' => false,
                'message' => 'Erreur de validation',
                'errors' => $validator->errors()
            ], 422);
        }

        // Vérifier si le costume existe
        $costume = Costume::find($request->costume_id);
        if (!$costume) {
            return response()->json([
                'success' => false,
                'message' => 'Le costume demandé n\'existe pas'
            ], 404);
        }

        // Vérifier la disponibilité (chevauchement de dates)
        $overlap = Location::where('costume_id', $request->costume_id)
            ->whereNotIn('statut', ['annulée', 'terminée'])
            ->where(function ($q) use ($request) {
                $q->where('date_debut', '<=', $request->date_fin)
                  ->where('date_fin', '>=', $request->date_debut);
            })
            ->exists();

        if ($overlap) {
            return response()->json([
                'success' => false,
                'message' => 'Ce costume est déjà réservé sur cette période'
            ], 409);
        }

        // Calcul du nombre de jours et du prix total
        $dateDebut = Carbon::parse($request->date_debut);
        $dateFin = Carbon::parse($request->date_fin);
        $days = $dateDebut->diffInDays($dateFin) + 1; // inclusif
        $prixJour = (float) $costume->prix;
        $prixTotal = $days * $prixJour;

        try {
            // Créer la location avec costume_id si disponible
            $location = Location::create([
                'nom_client' => $request->nom_client,
                'telephone' => $request->telephone,
                'nom_costume' => $costume->nom, // pour compatibilité avec l'ancien champ
                'costume_id' => $costume->id,
                'user_id' => $user->id,
                'date_debut' => $request->date_debut,
                'date_fin' => $request->date_fin,
                'taille' => $request->taille,
                'note' => $request->note,
                'statut' => $request->statut ?? 'en_attente',
                'prix_total' => $prixTotal,
            ]);

            return response()->json([
                'success' => true,
                'message' => 'Location réussie !',
                'data' => $location
            ], 201);

        } catch (\Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Erreur lors de la création de la location',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    /**
     * Récupérer toutes les locations
     */
    public function index(Request $request)
    {
        try {
            $locations = Location::with('costume')
                ->when($request->get('user_id'), function ($q, $userId) {
                    $q->where('user_id', $userId);
                })
                ->orderBy('created_at', 'desc')
                ->get();
            
            return response()->json([
                'success' => true,
                'data' => $locations,
                'count' => $locations->count()
            ], 200);

        } catch (\Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Erreur lors de la récupération des locations',
                'error' => $e->getMessage()
            ], 500);
        }
    }

    /**
     * Récupérer les locations de l'utilisateur connecté
     */
    public function mine(Request $request)
    {
        try {
            $user = $request->user();
            if (!$user) {
                return response()->json([
                    'success' => false,
                    'message' => 'Authentification requise'
                ], 401);
            }

            $locations = Location::with('costume')
                ->where('user_id', $user->id)
                ->orderBy('created_at', 'desc')
                ->get();

            return response()->json([
                'success' => true,
                'data' => $locations,
                'count' => $locations->count()
            ], 200);

        } catch (\Exception $e) {
            return response()->json([
                'success' => false,
                'message' => 'Erreur lors de la récupération des locations',
                'error' => $e->getMessage()
            ], 500);
        }
    }
}
