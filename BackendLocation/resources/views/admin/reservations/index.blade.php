@extends('admin.layout')

@section('title', 'Admin - Réservations')

@section('content')
    <h1 class="h3 mb-3">Réservations</h1>

    <div class="card">
        <div class="table-responsive">
            <table class="table table-striped mb-0">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Client</th>
                    <th>Costume</th>
                    <th>Période</th>
                    <th>Statut</th>
                    <th>Note</th>
                    <th class="text-end">Action</th>
                </tr>
                </thead>
                <tbody>
                @foreach($reservations as $r)
                    <tr>
                        <td>{{ $r->id }}</td>
                        <td>
                            {{ $r->nom_client }}
                            @if($r->user)
                                <div class="text-muted small">{{ $r->user->email }}</div>
                            @endif
                        </td>
                        <td>{{ $r->costume?->nom ?? $r->nom_costume }}</td>
                        <td>{{ $r->date_debut }} → {{ $r->date_fin }}</td>
                        <td><span class="badge bg-secondary">{{ $r->statut }}</span></td>
                        <td class="text-truncate" style="max-width: 260px;">{{ $r->note }}</td>
                        <td class="text-end">
                            <form method="POST" action="{{ route('admin.reservations.status', $r) }}" class="d-inline-flex gap-2">
                                @csrf
                                @method('PATCH')
                                <select name="statut" class="form-select form-select-sm">
                                    @foreach(['en_attente','confirmée','annulée','terminée'] as $s)
                                        <option value="{{ $s }}" @selected($r->statut === $s)>{{ $s }}</option>
                                    @endforeach
                                </select>
                                <button class="btn btn-sm btn-outline-primary">OK</button>
                            </form>
                        </td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3">
        {{ $reservations->links() }}
    </div>
@endsection


