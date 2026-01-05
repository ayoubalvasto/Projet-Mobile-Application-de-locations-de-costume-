@csrf
<div class="mb-3">
    <label class="form-label">Nom</label>
    <input class="form-control" name="nom" value="{{ old('nom', $costume->nom ?? '') }}" required>
</div>
<div class="mb-3">
    <label class="form-label">Taille</label>
    <input class="form-control" name="taille" value="{{ old('taille', $costume->taille ?? '') }}" required>
</div>
<div class="mb-3">
    <label class="form-label">Prix</label>
    <input class="form-control" name="prix" type="number" step="0.01" min="0" value="{{ old('prix', $costume->prix ?? '') }}" required>
</div>
<div class="mb-3">
    <label class="form-label">Image (URL)</label>
    <input class="form-control" name="image" value="{{ old('image', $costume->image ?? '') }}">
</div>

