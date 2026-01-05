@extends('admin.layout')

@section('title', 'Admin - Ajouter costume')

@section('content')
    <h1 class="h3 mb-3">Ajouter un costume</h1>

    <div class="card p-3">
        <form method="POST" action="{{ route('admin.costumes.store') }}">
            @include('admin.costumes.form', ['costume' => null])
            <button class="btn btn-primary">Créer</button>
            <a class="btn btn-link" href="{{ route('admin.costumes.index') }}">Retour</a>
        </form>
    </div>
@endsection


