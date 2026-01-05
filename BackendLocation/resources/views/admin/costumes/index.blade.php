@extends('admin.layout')

@section('title', 'Admin - Costumes')

@section('content')
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Costumes</h1>
        <a class="btn btn-primary" href="{{ route('admin.costumes.create') }}">Ajouter</a>
    </div>

    <div class="card">
        <div class="table-responsive">
            <table class="table table-striped mb-0">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Taille</th>
                    <th>Prix</th>
                    <th>Image</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>
                <tbody>
                @foreach($costumes as $c)
                    <tr>
                        <td>{{ $c->id }}</td>
                        <td>{{ $c->nom }}</td>
                        <td>{{ $c->taille }}</td>
                        <td>{{ $c->prix }}</td>
                        <td class="text-truncate" style="max-width: 240px;">{{ $c->image }}</td>
                        <td class="text-end">
                            <a class="btn btn-sm btn-outline-secondary" href="{{ route('admin.costumes.edit', $c) }}">Modifier</a>
                            <form action="{{ route('admin.costumes.destroy', $c) }}" method="POST" class="d-inline">
                                @csrf
                                @method('DELETE')
                                <button class="btn btn-sm btn-outline-danger" onclick="return confirm('Supprimer ?')">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                @endforeach
                </tbody>
            </table>
        </div>
    </div>

    <div class="mt-3">
        {{ $costumes->links() }}
    </div>
@endsection


